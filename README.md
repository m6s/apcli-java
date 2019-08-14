# WIP

Usage:

`apcli cp-project origproj newproj --from-package com.example.origproj --to-package com.example.newproj`

Building:

`./gradlew nativeImage`

Installing:

`sudo cp build/graal/apcli /usr/local/bin` 

TODO:

- [ ] `applicationId "info.mschmitt.showcase.app"`
- [ ] `testInstrumentationRunner 'info.mschmitt.showcase.app.TestRunner'`
- [ ] Parse `settings.gradle`