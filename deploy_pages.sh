#!/usr/bin/env bash

set -o errexit -o nounset

rm -rf gh-pages

mkdir gh-pages

cd gh-pages

git clone --depth=50 --branch=gh-pages https://$GH_TOKEN@github.com/chiknrice/concordion-setvar-extension.git

VER="${TRAVIS_TAG:-latest}"

if [ $VER == "latest" ];
then
    rm -rf concordion-setvar-extension/$VER
fi

mkdir concordion-setvar-extension/$VER

cp -R ../build/reports/spec/* concordion-setvar-extension/$VER

cd concordion-setvar-extension

git add .
git commit -m "Update concordion spec result"

git push
