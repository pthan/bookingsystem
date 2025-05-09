package com.my.bookingsystem.purchasepackage.service.serviceimpl;

import com.my.bookingsystem.domain.response.ResponseFormat;
import com.my.bookingsystem.mapper.PurchasePackageMapper;
import com.my.bookingsystem.mock.service.MockEmailService;
import com.my.bookingsystem.mock.service.MockPaymentService;
import com.my.bookingsystem.purchasepackage.dto.request.PurchasePackageRequest;
import com.my.bookingsystem.purchasepackage.dto.request.UserCreditPackageSearchRequest;
import com.my.bookingsystem.purchasepackage.dto.response.PurchasePackageListResponse;
import com.my.bookingsystem.purchasepackage.dto.response.PurchasePackageResponse;
import com.my.bookingsystem.purchasepackage.entity.CreditPackage;
import com.my.bookingsystem.purchasepackage.entity.PurchaseCreditPackage;
import com.my.bookingsystem.purchasepackage.repository.PackageRepository;
import com.my.bookingsystem.purchasepackage.repository.PurchaseCreditPackageRepository;
import com.my.bookingsystem.purchasepackage.service.PurchaseCreditPackageService;
import com.my.bookingsystem.shared.exceptions.BusinessException;
import com.my.bookingsystem.user.entity.User;
import com.my.bookingsystem.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;


@Slf4j
@Service
@AllArgsConstructor
public class PurchaseCreditPackageServiceImpl implements PurchaseCreditPackageService {

    private final PurchaseCreditPackageRepository purchaseCreditPackageRepository;
    private final PackageRepository packageRepository;
    private  final UserRepository userRepository;
    private  final PurchasePackageMapper purchasePackageMapper;
    private final MockPaymentService mockPaymentService;
    private final MockEmailService mockEmailService;

    @Override
    public ResponseFormat purchaseCreditPackage(PurchasePackageRequest req, Long userId) {
        ResponseFormat responseFormat=null;
        User user=new User();
        log.info("purchaseCreditPackage() called by user={} with packageId={} amount={}", userId, req.getPackageId(), req.getAmount());
        try {
        CreditPackage creditPackage = packageRepository.findById(req.getPackageId())
                .orElseThrow(() -> new EntityNotFoundException("Package not found"));

        user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        boolean validCard = mockPaymentService.addPaymentCard(req.getCardNumber(), req.getCvc(), req.getExpiryDate());
            if (!validCard) {
                throw new BusinessException("Invalid payment information.");
            }
        boolean charged = mockPaymentService.chargePayment(userId.toString(), creditPackage.getPrice());
            if (!charged) {
                throw new BusinessException("Payment failed.");
            }

        PurchaseCreditPackage purchaseCreditPackage = new PurchaseCreditPackage();
        purchaseCreditPackage.setGuid(UUID.randomUUID().toString());
        purchaseCreditPackage.setCreditPackage(creditPackage);
        purchaseCreditPackage.setUser(user);
        purchaseCreditPackage.setAvailableCredit(creditPackage.getCredit());
        purchaseCreditPackage.setRemainingCredit(creditPackage.getCredit());
        purchaseCreditPackage.setAmount(req.getAmount());
        purchaseCreditPackage.setExpireIn(creditPackage.getExpireIn());

        purchaseCreditPackage.setCreatedOn(ZonedDateTime.now());
        purchaseCreditPackage.setStatus("Active");
        purchaseCreditPackage.setPaymentStatus("PAID");
        purchaseCreditPackage.setExpireDate(ZonedDateTime.now().plusDays(creditPackage.getExpireIn()));
        purchaseCreditPackage.setCreatedBy(userId);
        purchaseCreditPackageRepository.save(purchaseCreditPackage);

        PurchasePackageResponse purchasePackageResponse = purchasePackageMapper.toPurchasePackageResponse(purchaseCreditPackage);
        purchasePackageResponse.setPackageName(creditPackage.getPackageName());

        responseFormat=new ResponseFormat();
        responseFormat.setSuccess(true);
        responseFormat.setMessage(Optional.of("Package creation success!"));
        responseFormat.setData(Optional.of(purchasePackageResponse));
        log.info("Purchased credit package name:{} at createdAt:{}",creditPackage.getPackageName(),creditPackage.getCreatedOn());
        }catch (Exception e){
            log.error("error at creating records  ",e);
            return ResponseFormat
                    .failedResponse()
                    .message("Processing failed, please try again later!")
                    .data("Processing failed, please try again later!")
                    .build();
        }
        boolean sent = mockEmailService.sendVerificationEmail(req.getEmail(), user.getName(),"Purchase Confirm","Purchase success");
        if (!sent) {
            log.warn("Email confirmation failed to send.");
        }

        return  responseFormat;
    }
public ResponseFormat listUserCreditPackages(UserCreditPackageSearchRequest request, Long userId) {
    log.info("listUserCreditPackages() called by user={} with request={} ", userId, request);

    // defaults
    int page = request.getFirst() == null ? 0 : request.getFirst();
    int size = request.getMax() == null ? Integer.MAX_VALUE : request.getMax();
    String orderBy = request.getOrderBy() == null ? "id" : request.getOrderBy();
    boolean asc = request.getAsc() == null ? false : request.getAsc();

    Sort sort = asc ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();
    Pageable pageable = PageRequest.of(page, size, sort);
    ResponseFormat responseFormat=null;
    // build dynamic filter spec
    Specification<PurchaseCreditPackage> spec = (root, query, cb) -> cb.equal(root.get("user").get("id"), userId);

    if (request.getKeyword() != null && !request.getKeyword().isBlank()) {
        spec = spec.and((root, query, cb) -> cb.like(
                cb.lower(root.get("creditPackage").get("packageName")),
                "%" + request.getKeyword().toLowerCase() + "%"
        ));
    }
    if (request.getStatus() != null) {
        spec = spec.and((root, query, cb) -> cb.equal(
                root.get("status"),
                Boolean.TRUE.equals(request.getStatus())
                        ? PurchaseCreditPackage.PACKAGE_STATUS_ACTIVE
                        : PurchaseCreditPackage.PACKAGE_STATUS_INACTIVE
        ));
    }
    if (request.getIsExpire() != null) {
        LocalDateTime now = LocalDateTime.now();
        if (request.getIsExpire()) {
            spec = spec.and((root, query, cb) -> cb.lessThan(root.get("expireDate"), now));
        } else {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("expireDate"), now));
        }
    }
    if (request.getHasBalance() != null) {
        if (request.getHasBalance()) {
            spec = spec.and((root, query, cb) -> cb.greaterThan(root.get("remainingCredit"), 0));
        } else {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("remainingCredit"), 0));
        }
    }

    try {
        Page<PurchaseCreditPackage> pageResult = purchaseCreditPackageRepository.findAll(spec, pageable);

        PurchasePackageListResponse response = PurchasePackageListResponse.builder()
                .items(new ArrayList<>())
                .totalRecords(pageResult.getTotalElements())
                .build();

        for (PurchaseCreditPackage pp : pageResult.getContent()) {
            PurchasePackageResponse dto = new PurchasePackageResponse();
            dto.setId(pp.getId());
            dto.setPackageName(pp.getCreditPackage().getPackageName());
            dto.setAvailableCredit(pp.getAvailableCredit());
            dto.setRemainingCredit(pp.getRemainingCredit());
            dto.setExpireDate(pp.getExpireDate());
            dto.setStatus(pp.getStatus());
            response.getItems().add(dto);
        }

        responseFormat = new ResponseFormat();
        responseFormat.setSuccess(true);
        responseFormat.setMessage( Optional.of("CreditPackage list successful") );
        responseFormat.setData( Optional.of(response) );
        log.info("Successfully fetching purchase package list and found total {} records",response.getTotalRecords());
    } catch (Exception e) {
        log.error("Error fetching packages for user={}", userId, e);
        return ResponseFormat.failedResponse()
                .message("Processing failed, please try again later!")
                .data("Processing failed, please try again later!")
                .build();
    }
    return responseFormat;
}

}
