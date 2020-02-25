#!/bin/bash
# init
function pause(){
   read -p "$*"
}

"C:\Program Files\Java\jre1.8.0_231\bin\java.exe" -version
"C:\Program Files\Java\jre1.8.0_231\bin\java.exe" -jar Exile-Buddy-0.0.0.jar

pause 'Press <Enter> key to continue...'