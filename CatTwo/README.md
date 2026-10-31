# CAT II – Shopping calculator (BBIT 2203)

Package `com.example.cattwo` · same build setup as CAT One (JDK 17, Gradle 8.7, SDK 34).

## Unit prices (hard-coded in `MainActivity.java`)
Bread KES 2,500 · Milk KES 3,000 · Sugar KES 4,000

## Discount rules used
| Grand total | Discount |
|---|---|
| below 11,000 | none |
| 11,000 to 25,999 | 15% |
| 26,000 to 35,000 (35,000 included) | 25% |
| over 35,000 | 30% |

The question leaves gaps (10,001 to 10,999 and 25,001 to 25,999); each band here runs up to the start of the next.

## Screenshots to take for part (d): one per case
| Bread | Milk | Sugar | Grand total | Discount | Net pay |
|---|---|---|---|---|---|
| 1 | 1 | 1 | 9,500.00 | 0.00 | 9,500.00 |
| 2 | 2 | 2 | 19,000.00 | 2,850.00 | 16,150.00 |
| 3 | 3 | 3 | 28,500.00 | 7,125.00 | 21,375.00 |
| 4 | 4 | 4 | 38,000.00 | 11,400.00 | 26,600.00 |

Also screenshot the two Toasts: leave a quantity empty ("You must enter quantity") and type 5 ("You cannot buy more than 4 items").

## Build
In a Codespace: `cd CatTwo && gradle assembleDebug`, then download `app/build/outputs/apk/debug/app-debug.apk`.
If Gradle can't find the SDK, run `echo "sdk.dir=$HOME/android-sdk" > local.properties` inside `CatTwo` first.
