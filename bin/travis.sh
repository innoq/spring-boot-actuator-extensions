#!/usr/bin/env bash
#
# Copyright 2017 innoQ Deutschland GmbH
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#


set -euo pipefail
IFS=$'\n\t'

# log functions
log()   { echo "$*" >&2; }
info()  { log "$*";      }
warn()  { log "[!] $*";  }
error() { log "[!!] $*"; }


# check environment
if [ -z "${TRAVIS_BRANCH}" ]; then
  error "TRAVIS_BRANCH not set"
  exit 1
fi

if [ -z "${TRAVIS_PULL_REQUEST}" ]; then
  error "TRAVIS_PULL_REQUEQST not set"
  exit 1
fi

#if [ -z "${SONAR_URI}" ]; then
  #error "SONAR_URI not set"
  #exit 1
#fi

#if [ -z "${SONAR_TOKEN}" ]; then
  #error "SONAR_TOKEN not set"
  #exit 1
#fi


# travis helper functions
is_pull_request() {
  [ "${TRAVIS_PULL_REQUEST}" != "false" ]
}

on_master() {
  [ "${TRAVIS_BRANCH}" = "master" ]
}


main() {
  if is_pull_request; then
    log "Building Pull Request"
    ./mvnw -B -e test
  else
    log "Building branch: ${TRAVIS_BRANCH}"
    #if on_master; then
    #  ./mvnw -B -e \
    #    clean org.jacoco:jacoco-maven-plugin:prepare-agent package sonar:sonar \
    #    -Dsonar.host.url="${SONAR_URI}" -Dsonar.login="${SONAR_TOKEN}"
    #else
    ./mvnw -B -e test
    #fi
  fi
}

main "$@"

