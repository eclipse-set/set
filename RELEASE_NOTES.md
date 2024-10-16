# Eclipse Signalling Engineering Toolbox: Release notes 
The Eclipse Signalling Engineering Toolbox provides means for initialization, managing, testing, validation, visualization, documentation and merging of instances of the object model for the railway interlocking signalling technology.
## 2.3
- Initial implementation of the Overview Plan, which provides an abstract visualization of the topology.
- Importing of existing data into other planning projects
- Initial implementation of the ETCS Tables (Ssza, Sszs, Sszw)
- All ESTW tables displayed separately according to setting ranges via a selection box (note: this selection has no influence on the ETCS tables).
  
## 2.2
SET 2.2.0 adds a siteplan visualizing the geographical structure of the railway project. It also adds functionality to edit project metadata and resolves various minor bugs. 

## 2.1
SET 2.1.0 adds a new planning table for intermittent automatic train running control devices (Sskp) and resolves various minor issues of the previous release 2.0.0.

## 2.0
As first major feature release as part of the Eclipse Foundation, SET 2.0 adds support for the PlanPro data model version 1.10.0.1 including updated PT1 tables.
Additionally the following additional features and improvements were completed:

- A component for validating model plausibility and consistency ("PlaZ Modell") was added.
- Performance and consistency of table exports (PDF/Excel) were improved.
- Eclipse Platform has been updated to Eclipse 2022-12.
- Improved handling of loading incorrect model versions.

## 1.1
First release of the Eclipse Signalling Engineering Toolbox as an Eclipse project. The Signalling Engineering Toolbox provides means for initialization, managing, testing, validation, visualization, documentation and merging of instances of the object model for the railway interlocking signalling technology.

As an initial release, most work has been towards migrating the development process to the Eclipse Foundation. However some minor features and improvements were also completed:
- Improved consistency of "jump to"-actions
- Webservers have been replaced by a faster callback-based architecture in our browser integration
- Eclipse Platform has been updated to Eclipse 2022-06
- A Java Runtime Environment is now included in the binary artifact
