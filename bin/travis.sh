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


# travis helper functions
is_pull_request() {
  [ "${TRAVIS_PULL_REQUEST}" != "false" ]
}

on_master() {
  [ "${TRAVIS_BRANCH}" = "master" ]
}


# main
main() {
  if is_pull_request; then
    log "Building Pull Request: #${TRAVIS_PULL_REQUEST}"
    if [ -n "${SONAR_GITHUB_TOKEN-}" ]; then
      ./mvnw -B -e \
        clean org.jacoco:jacoco-maven-plugin:prepare-agent package sonar:sonar \
        -Dsonar.analysis.mode=preview \
        -Dsonar.github.repository="${TRAVIS_REPO_SLUG}" \
        -Dsonar.github.pullRequest="${TRAVIS_PULL_REQUEST}"
    else
      ./mvnw -B -e test
    fi
  else
    log "Building branch: ${TRAVIS_BRANCH}"
    if on_master; then
      ./mvnw -B -e \
        clean org.jacoco:jacoco-maven-plugin:prepare-agent package sonar:sonar
    else
      ./mvnw -B -e test
    fi
  fi
}

main "$@"

