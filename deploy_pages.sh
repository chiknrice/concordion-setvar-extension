#!/usr/bin/env bash

set -o errexit -o nounset

rm -rf gh-pages

mkdir gh-pages

cd gh-pages

git clone --depth=50 --branch=gh-pages https://$GH_TOKEN@github.com/chiknrice/concordion-setvar-extension.git

echo "Adding concordion specs for $TRAVIS_TAG"
mkdir concordion-setvar-extension/$TRAVIS_TAG
cp -R ../build/reports/spec/* concordion-setvar-extension/$TRAVIS_TAG

rm -rf concordion-setvar-extension/latest
mkdir concordion-setvar-extension/latest
cp -R ../build/reports/spec/* concordion-setvar-extension/latest

cd concordion-setvar-extension

echo "Updating latest concordion specs"

git config user.email "chiknrice@gmail.com"
git config user.name "Travis CI"
git config push.default matching

git add --all .
git commit -m "Update concordion spec result"

git push

