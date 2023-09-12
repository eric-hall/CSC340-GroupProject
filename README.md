# CSC340-GroupProject

## Title
- Restaurant Ready!

## Team Members
- Eric Hall
- Sam Buttonow
- Rizik Haddad

## Point-of-Sale System

### Description
- A simple Point-of-Sale System (PoSS) for restaurants.
- Client profile-oriented, multi-purpose GUI front-end.
- Cashier terminal constructs + submits customer orders + payment.
- Kitchen terminal manages + fulfills submitted orders.
- Administrative system creates sales reports and modifies menu repository.
- Internal payment tracking system responsible for charges incurred by orders.

### Cashier Terminal Interface CTI (touchscreen)
- Profile log-in/log-out (password?)
  - Interface with Attendance Tracker System (ATS)
- Select cashier profile (password?)
  - Profile actions
    - Print current sales report
    - Log-out (unsatisfied tickets?)
  - Manage active ticket orders (start new, etc.)
    - Select/modify customer's order (menu items)
    - Submit to kitchen
    - Facilitate transactions
    - Terminate orders (admin approval?)
    
### Kitchen Display System KDS (touchscreen)
- Display active ticket queue w/ informative notes
- Options to modify ticket priority in queue
- Holds for server-defined course breaks
- Mark tickets as "in-progress", "waiting", or "ready"

### Administrator System (password required)
- Manages Cashier profiles
  - Create/delete/update profile information for CTI
  - "Bird's-eye view" of Cashier's sales
- CRUD menu repository
  - Define new menu items + layout Cashier interface buttons
  - Define prices + inventory limits
- Query bill-of-sales system
  - Per-cashier or daily reports
