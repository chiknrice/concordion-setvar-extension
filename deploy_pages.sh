#!/usr/bin/env bash

set -o errexit -o nounset

mkdir gh-pages

cd gh-pages

git init
git config user.name "Travis CI"
git config user.email "chiknrice@gmail.com"

cp -R ../build/reports/spec/* .

git add .
git commit -m "Update concordion spec result"

git push --force --quiet  "https://$GH_TOKEN@github.com/chiknrice/concordion-setvar-extension.git" master:gh-pages > /dev/null 2>&1
