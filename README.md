# Project Summary / Overview

With the growing use of health trackers and health research, it can be difficult to keep up with the many calculations and methods
used, and even more difficult to find everything in one place. This service aims to centralize those calculations into one location with flexible usage.
Generic users will be included in the service to allow displays of health metrics across various physical characteristics as well as having the capability
to set or reuse common physical qualities to more easily return reused health metrics.

The service will provide:
- Retrieving all people entities
- Retrieve people by id
- Add a person
- Delete a person
- Update a person
- Retrieve health metrics directly w/user-entered BF% (uses optional field for BF%)
- Retrieve health metrics directly w/calculated BF% (does not use optional field for BF%)

* retrieving health metrics directly is the same as retrieving a person by ID, but without extra steps (better for one-off custom values)

## RESOURCES

### People

| Property          | Description             | Type/Format |
|-------------------|-------------------------|-------------|
| id                | unique identifier       | number      |
| age               | Person's age            | number      |
| sex               | Person's biological sex | string      |
| height            | Person's height         | number      |
| weight            | Person's weight         | number      |
| bodyfat(optional) | Person's body fat %     | number      |


### Calculations

| Property             | Description                    | Type/Format |
|----------------------|--------------------------------|-------------|
| BMI                  | body mass index                | number      |
| BMR                  | basal metabolic rate           | number      |
| LBM                  | lean body mass                 | number      |
| bodyfat (calculated) | body fat %                     | number      |
| IBW                  | ideal body weight              | number      |
| TDEE                 | total daily energy expenditure | number      |

### Considerations
- IBW may or may not be desirable, it can be fairly inaccurate and relies on height being 5ft or above
- TDEE - we can calculate TDEE with/without BF%, with uses a different set of equations
  - estimated BF% through calculations may/may not be accurate
  - we could possibly make user-entered bf% optional, or have a different API method to retrieve that unique info


## SERVICE CALLS
### Person
- GET/people/json/
- GET/people/json/:id
- POST/people
- PUT/people/:id
- DELETE/people/:id

### Calculations
- GET/calculation/json/:age:height:weight
- GET/calculation/json/:age:height:weight:bodyfat (uses a different formula for TDEE w/BF%)

### Response Examples
All Json responses

#### Person responses
Example response of getting all people/demo profiles. Getting by ID will just return a single object instead of a collection
- Might need to view in 'edit' mode, formatting in display mode was not as intended
[

  {
    "id": "1"
    "height": "70",
    "weight": "200",
    "sex": "male",
    "bodyfat": "",
    "calculations": {
      "BMI": "value",
      "BMR": "value",
      "LBM": "value",
      "bodyfat": "value",
      "IBW": "value",
      "TDEE": "value"
    }
  },
  {
    "id": "2"
    "height": "65",
    "weight": "150",
    "sex": "male",
    "bodyfat": "15",
    "calculations": {
      "BMI": "value",
      "BMR": "value",
      "LBM": "value",
      "bodyfat": "value",
      "IBW": "value",
      "TDEE": "value"
    }
  },
  {...}

]

#### Calculation-only responses
We can choose to return both the submitted attributes and calculated values (A), or only the calculated values (B).

##### Example A:
  {
    "height": "70",
    "weight": "200",
    "sex": "male",
    "bodyfat": "",
    "calculations": {
      "BMI": "value",
      "BMR": "value",
      "LBM": "value",
      "bodyfat": "value",
      "IBW": "value",
      "TDEE": "value"
    }
  }
##### Example B:
{
  "BMI-value": "value",
  "BMR-value": "value",
  "LBM-value": "value",
  "bodyfat-value": "value",
  "IBW-value": "value",
  "TDEE-value": "value"
  ]
}

### Considerations
- I'm thinking the way this would work is we have a collection of people objects of varying heights, sex, weight, etc... and each would have their own collection of health metrics/data
- Something like: people{ age, weight, height, etc.., metrics[BMI, BMR, IBW, etc...] }
- When a person is returned, their physical data/qualities are used to calculate and return a collection of health metric data alongside age/height/etc..
- Ideally, I'd want to have a way of returning a collection of health metric data by entering the raw parameters for people objects instead of returning a predefined people object


## Calculations
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

### BMR with BF - Katch-McArdle equation:
(this equation is an entirely different formula for calculating TDEE, it does not make man/woman distinctions in LBM calculation)
- LBM = weight * (1 - body fat % / 100)
- BMR = 370 / (9.8 * LBM)
- TDEE = BMR * activity level
- activity levels:
    - sedentary - 1.2             --office job with no exercise
    - lightly active - 1.375      --exercise 1-2 times a week
    - moderately active - 1.725   --exercise 3-5 times a week
    - extra active - 1.9          --exercise 6+ times a week