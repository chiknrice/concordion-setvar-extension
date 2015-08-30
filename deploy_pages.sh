#!/usr/bin/env bash

set -o errexit -o nounset

rm -rf gh-pages

mkdir gh-pages

cd gh-pages

git clone --depth=50 --branch=gh-pages https://$GH_TOKEN@github.com/chiknrice/concordion-setvar-extension.git

if [[ -n "${TRAVIS_TAG-}" ]]
then
    echo "Adding concordion specs for $TRAVIS_TAG"
    mkdir concordion-setvar-extension/$TRAVIS_TAG
    cp -R ../build/reports/spec/* concordion-setvar-extension/$TRAVIS_TAG
fi

rm -rf concordion-setvar-extension/latest
mkdir concordion-setvar-extension/latest
cp -R ../build/reports/spec/* concordion-setvar-extension/latest

cd concordion-setvar-extension

# currently always evaluates to true due to the timestamps in spec footers
if [[ -n $(git status -s) ]]
then
    echo "Updating latest concordion specs"

    git config user.email "chiknrice@gmail.com"
    git config user.name "Travis CI"
    git config push.default matching

    git add .
    git commit -m "Update concordion spec result"

    git push
fi
