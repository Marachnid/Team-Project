# Project Summary / Overview

API Service: Body Composition Calculator

With the growing usage of health trackers, research, and awareness of personal wellbeing and health, individuals have more power than ever to 
track their health and body composition. However, with the many variations in formulas, complicated conversions from metric to imperial units, and 
different applications of these calculations, it can be difficult and arduous to find or create health applications revolving around these calculations and metrics.
This service seeks to create an API that centralizes a number of popularly used health metrics and calculations into one location 
and only needing a physical Profile (age, height, weight, biological sex, and activity level) to calculate estimations. 

The service uses age, height, weight, biological sex, and activity level to calculate and return estimations for a Profile's: 
- Body Mass Index (BMI)
- Basal Metabolic Rate (BMR)
- Lean Body Mass (LBM)
- Body Fat percentage 
- Total Daily Energy Expenditure (TDEE)
- Ideal Body Weight (IBW)


## Endpoints
### RESTful
#### Path 'services/profile'
RESTful endpoints consist of HTTP GET/PUT/POST/DELETE methods
that access/modify generic Profile entities to show how calculations change from one set (Profile) of physical characteristics to another. When returning a Profile entity, it will return
all persistent profile characteristics as well as a collection of calculations performed on said Profile. This creates an easily reusable/callable sample of health calculations/metrics for a 
given body type, age, etc...

- [GET all profiles](project-documents/screenshots/API-Profile-GET-All.png)
- [GET profile by ID](project-documents/screenshots/API-Profile-GET.png)
- [PUT profile by ID](project-documents/screenshots/API-Profile-PUT.png)
- [POST profile by ID](project-documents/screenshots/API-Profile-POST.png)
- [DELETE profile by ID](project-documents/screenshots/API-Profile-DELETE.png)


### Non-RESTful
#### Path 'services/calculate'
The non-RESTful endpoints are lightweight versions of the RESTful endpoints. These endpoints do not have any DB interactions or state and perform the same calculations as
the RESTful /profile endpoints, but it receives the raw parameters instead of using HTTP methods. After receiving the raw parameters, there is a /full path that returns 
both the performed calculations and parameters received - similar to a /profile/GET method. The other path is /light and it only returns the calculations, nothing more.

The goal of these endpoints was to provide a lightweight and quick way to use the service and control which kind of responses/values to receive.

- [Calculate values with FULL response](project-documents/screenshots/API-Calculate-FULL.png)
- [Calculate values with LIGHT response](project-documents/screenshots/API-Calculate-LIGHT.png)

## RESOURCES
### Profile attributes
| Property | Description                               | Type/Format |
|----------|-------------------------------------------|-------------|
| id       | unique identifier                         | int         |
| age      | Physical characteristic - age             | int         |
| height   | Physical characteristic - height          | double      |
| weight   | Physical characteristic - weight          | double      |
| sex      | Physical characteristic - biological sex  | String      |
| activity | Physical characteristic - weekly activity | double      |


### Calculation attributes
| Property | Description                    | Type/Format |
|----------|--------------------------------|-------------|
| BMI      | body mass index                | double      |
| BMR      | basal metabolic rate           | double      |
| LBM      | lean body mass                 | double      |
| bodyfat  | body fat %                     | double      |
| IBW      | ideal body weight              | double      |
| TDEE     | total daily energy expenditure | double      |





## CALCULATIONS
### Body mass index (BMI) - assessment/ratio of body weight relative to height
- BMI = Weight * 703 / height^2

### Basal(base) metabolic rate (BMR) - the number of calories burned while at rest (with no activity/exercise)
- Men: BMR = 66 + (6.23 * weight) + (12.7 * height) - (6.8 * age)
- Women: BMR = 655 + (4.35 * weight) + (4.7 * height) - (4.7 * age)

### Lean Body Mass (LBM) - the amount of non-fat body mass a person holds
- Men: LBM = (0.407 * weight) + (0.267 * height) - 19.2
- Women: LBM = (0.252 * weight) + (0.473 * height) - 48.3

### Body fat percentage - the amount of body fat a person holds
(estimated, can be/is inaccurate)
- Men: BF% = (1.2 * BMI) + (0.23 * age) - 16.2
- Women: BF% = (1.2 * BMI) + (0.23 * age) - 5.4

### Total Daily Energy Expenditure (TDEE) - the total amount of calories burned in a day (BMR + activity/exercise)
(we'll need to include instructions explaining activity levels / rough estimations)
- TDEE = BMR * activity level --- (use BMR from above)
- activity levels:
  - sedentary - 1.2             --office job with no exercise
  - lightly active - 1.375      --exercise 1-2 times a week
  - moderately active - 1.725   --exercise 3-5 times a week
  - extra active - 1.9          --exercise 6+ times a week

### Ideal body weight (IBW) - estimate of healthy weight range based on age and weight
(assumes heights start at 5ft, individuals shorter than 5ft may need to adjust things -- on the fence about using this)
- Men: IBW = 50 + (2.3 * (height in inches over 60))
- Women: IBW = 45.5 (2.3 * (height in inches over 60))