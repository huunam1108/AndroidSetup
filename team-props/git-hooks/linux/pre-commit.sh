#!/usr/bin/env bash
echo "Running static analysis..."

echo "Start running ktlint"
./gradlew ktlintFormat ktlintCheck --daemon
status0=$?
if [[ "$status0" = 0 ]] ; then
    echo "ktlint found no problems."
else
    echo 1>&2 "ktlint found violations it could not fix. Please check ktlint report at \"build/reports/ktlint/ktlint.html\" "
    exit 1
fi

echo "Start running detektCheck"
./gradlew detekt --daemon
status1=$?
if [[ "$status1" = 0 ]] ; then
    echo "detekt found no problems."
else
    echo 1>&2 "ktlint found violations. Please check ktlint report at \"build/reports/detekt/detekt-report.html\" "
    exit 1
fi

echo "Start running unit test"
./gradlew testDebug --daemon
status2=$?
if [[ "$status2" = 0 ]] ; then
    echo "run unit test success."
    git add .
else
    echo 1>&2 "run unit test failure, please re-check"
    exit 1
fi
