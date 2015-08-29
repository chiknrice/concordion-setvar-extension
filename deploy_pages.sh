#!/usr/bin/env bash

set -o errexit -o nounset

git checkout gh-pages

cp -R build/reports/spec/* .

git add .

git commit -m "Update concordion spec result"

git push "https://$GH_TOKEN@github.com/chiknrice/concordion-setvar-extension.git"
