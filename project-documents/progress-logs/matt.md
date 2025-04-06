## Week of 3/24

- Setup initial project repo with starting framework and sample files
- Uploaded Pete's charter/resume
- Added team members as collaborators
- Thought out and came up with a plan for the Health API idea
- Put together a high level design document in project README - problem statement, resources, service calls, calculations


## Week of 3/31

- Created initial CalculateMetrics class - built out the framework and URI paths, left calculations and testing of data for Andrew
- Looked over pull requests for additions by Pete and Wendy
- End of week
  - Had a big push to implement the backend DB, DAO, Hibernate, dependencies, and table configuration missing in the Profile api services
  - Separated work into a refactor package to not create a merge conflict nightmare. Some changes (like to the POM) are general though, and might have some conflicts
  - Built out a ProfileServices class - implemented DAO interactions, set URI paths, added exception handling for different scenarios
  - Created a reusable testing environment for DAO operations - DB rebuilds itself with data on each run - currently the same as the production/dev environment
  - Created GENERIC properties/hibernate files with instructions on how to set username/password/rename the file to make it work easily
  - Spent a little while refactoring naming conventions and setting/checking URI paths
  - Put together screenshots showing each URI/API call and their results from POSTMAN in project-documents/screenshots