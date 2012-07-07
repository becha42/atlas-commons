atlas-commons
=============
Commonly used functions for [RIPE NCC Atlas](http://atlas.ripe.net).

### Examples

#### Simple
The simplest example to fetch the measurement data into a String: https://gist.github.com/3064604

#### Plain
This is an example on how to use the API so you won't have to log in for each request:

	mvn -q exec:java -Dexec.mainClass=com.wnagele.atlas.commons.examples.Plain -Dexec.args="-username username -password password -url https://udm01.atlas.ripe.net/atlas/udm_download.json?attach=1&msm_id=1001234&y=2012&w=27"

#### DNS
Measurements with DNS data can be decoded using this example:

	mvn -q exec:java -Dexec.mainClass=com.wnagele.atlas.commons.examples.DNS -Dexec.args="-username username -password password -url https://udm01.atlas.ripe.net/atlas/udm_download.json?attach=1&msm_id=1001234&y=2012&w=27"