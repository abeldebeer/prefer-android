# Prefer for Android: Change Log

## [0.2.8](../../releases/tag/0.2.8) (2017-01-09)

- Makes `RxPrefs` extend `Prefs` for compatibility.

## [0.2.7](../../releases/tag/0.2.7) (2017-01-09)

- Introduces `OnGroupValueChanged` listener, to monitor changes to an entire pref group.

## [0.2.6](../../releases/tag/0.2.6) (2016-09-15)

- Adds feature for setting default preference titles in case a preference has no custom title.

## [0.2.5](../../releases/tag/0.2.5) (2016-08-17)

- Does not print error stacktrace when no Pref is found for changed `SharedPreferences` key.
- Upgrades Gradle plugins and wrapper.

## [0.2.4](../../releases/tag/0.2.4) (2016-08-08)

- Adds support for distribution of sources and javadoc.
- Optimizes gradle configuration.

## [0.2.3](../../releases/tag/0.2.3) (2016-08-05)

- Makes sure fragment text input preference defaults to single-line.
- Adds `android-maven` gradle plugin.

## [0.2.2](../../releases/tag/0.2.2) (2016-07-28)

- Reverts RxJava version to fix compatibility issues.

## [0.2.0](../../releases/tag/0.2.0) (2016-07-28)

- Renames "subscribe" (and related concepts) to "addListener" to prevent confusion with Rx 
extension.

## [0.1.4](../../releases/tag/0.1.4) (2016-07-19)

- Unsets default instance in `AndroidPreferProvider#disposeDefault()`.

## [0.1.3](../../releases/tag/0.1.3) (2016-06-17)

- Updates Guava Preconditions library.

## [0.1.2](../../releases/tag/0.1.2) (2016-06-15)

- Introduces `prefer-testing` module.
- Adds `AndroidPrefer#newGroup()`.
- Optimizes code re-use by introducing `PreferHelper` interface.

## [0.1.1](../../releases/tag/0.1.1) (2016-06-14)

- Fixes JitPack distribution issue related to test artifacts.

## [0.1.0](../../releases/tag/0.1.0) (2016-06-14)

- First release.

## [0.0.1](../../releases/tag/0.0.1) (2016-06-13)

- Development version.
