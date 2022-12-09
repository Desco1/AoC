# Advent of code (template)
A small [Idea](https://www.jetbrains.com/idea/) project that automatically fetches and saves your puzzle input, shamelessly adapted from [hughjdavey's template](https://github.com/hughjdavey/aoc-kotlin-starter).\
Has support for multiple days and multiple years, doesn't upload the solutions automatically to avoid unnecessary requests and fetched input is saved for repeated use.\
If you're just looking for solutions, [go to the solutions branch instead](https://github.com/Desco1/AoC/tree/solutions).

## Usage
[Create a repository from this template](https://docs.github.com/en/repositories/creating-and-managing-repositories/creating-a-repository-from-a-template), make sure to NOT include all branches, you should only be taking the template branch.\
To automatically parse puzzle input you need your session cookie, which you can get [in a multitude of ways](https://www.cookieyes.com/blog/how-to-check-cookies-on-your-website-manually/) after having logged in.\
After getting your session cookie, paste it in [/src/main/resources/session.txt](https://github.com/Desco1/AoC/blob/bdc4768c61fe0889374b97909d893d598465ba10/src/main/kotlin/com/desco/InputReader.kt#L19).\
Fetched input will be saved in a newly created puzzleinputs folder in the project root directory.\
To execute any day from its file, you may create a temporary function called main that simply calls the day's [both](https://github.com/Desco1/AoC/blob/bdc4768c61fe0889374b97909d893d598465ba10/src/main/kotlin/com/desco/Day.kt#L12), [first](https://github.com/Desco1/AoC/blob/bdc4768c61fe0889374b97909d893d598465ba10/src/main/kotlin/com/desco/Day.kt#L17) or [second](https://github.com/Desco1/AoC/blob/bdc4768c61fe0889374b97909d893d598465ba10/src/main/kotlin/com/desco/Day.kt#L21) methods.

## Notes
This project is not meant to be built onto a jar! This is just a tool to have an ordered workspace when playing AoC.\
Session cookies last over a month. You don't have to worry about it expiring if you just play in December, but you may have to refresh it every once in a while.
