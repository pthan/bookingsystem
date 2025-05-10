# 📚 Booking System API

This project is a backend API implementation for a mobile class booking system.  
It handles user registration, package purchase, class schedule viewing, credit-based booking, waitlist management, and concurrency-safe operations.

---

## 📦 Modules Implemented

### ✅ User Module
- Register / Login
- Role & permission-based access control
- Mock email verification included

### ✅ Package Module
- View available credit packages per country
- Purchase packages tied to users
- Track remaining credits and expiry dates

### ✅ Schedule & Booking Module
- View class schedules by country
- Book classes using packages from matching country
- Deduct credits from valid active packages
- Per-class concurrency lock (max 5 users)
- Waitlist management (FIFO)
- Cancel bookings and auto-promote from waitlist
- Prevent duplicate waitlist entries
- Refund credits on cancellation if > 4 hours before start
- Auto-refund waitlist users after class ends (via scheduled job)

---

## 🔒 Concurrency Control
- Uses Redisson `RLock` and `RAtomicLong`
- Enforces max 5 concurrent bookings per class
- Dynamically adapts to available slots < 5
- Protects against double booking and overbooking

---

## 🕒 Waitlist & Auto-Promotion
- Waitlist stored using booking with `WAITING` status
- When a booking is canceled, the next waitlist user is auto-promoted (FIFO)
- Scheduled refund job processes unused waitlist bookings after class ends

---

## 🧪 Mocked External Services

Used for testing email and payment flows without actual integration:

```java
public boolean SendVerifyEmail(params) { return true; }
public boolean AddPaymentCard(params) { return true; }
public boolean PaymentCharge(params) { return true; }
```

---

## 🛠️ Tech Stack
- Java 17
- Spring Boot
- Spring Data JPA (Hibernate)
- Redis + Redisson
- Lombok
- MapStruct
- MySQL (tested)
---

## 🚀 How to Run

1. Import the project into IntelliJ / VSCode
2. Configure DB in `application.properties`
3. Restore database into mysql (schema.sql ,data.sql)
4. Ensure Redis is running on `localhost:6379`
5. Run the main Spring Boot application
6. Use the included Postman collection to test endpoints
7. Authenticate using provided credentials before calling protected APIs

---

### 🔐 Authentication Test Accounts

#### Admin
- Email: `admin1@gmail.com`
- Password: `12345678`

#### User
- Email: `user1@gmail.com`
- Password: `12345678`

> Login Endpoint: `POST /api/auth/login`  
Use the returned token in API requests:

```
Authorization: Bearer <token>
```

---

## 📋 API List Request Format (Pagination + Search)

When using list APIs (class list, booking list, etc.), use the following format:

```json
{
  "keyword": "string",
  "first": 0,
  "max": 10,
  "orderBy": "startDate",
  "asc": true
}
```

### Explanation:
- `keyword`: Search field (e.g., class name)
- `first`: Page number (starting from 0)
- `max`: Number of records per page
- `orderBy`: Column to sort by
- `asc`: `true` = ascending, `false` = descending

> Leave all fields blank or use `{}` to fetch default full list.

---

## 🗄️ Database

- `schema.sql` file includes:
    - Sample users
    - Credit packages
    - Class schedules
    - Booking data (including waitlist)

---

## 📌 Optional Enhancements

- Class check-in endpoint
- Prevent overlapping class bookings
- Admin-level reporting (optional future feature)

---

## ✅ Final Notes

This project demonstrates full booking lifecycle coverage, concurrency protection, and FIFO-based waitlist logic.  
It is cleanly modularized and suitable for production-facing mobile app integration.
