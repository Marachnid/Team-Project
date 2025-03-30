## Problem Statement

pulled from project README


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
