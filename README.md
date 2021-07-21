# scramblies.main

generated using Luminus version "4.14"

FIXME

## Prerequisites

You will need [Leiningen][1] 2.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein run 

To compile & watch for changes on the front end, run:

``` sh
shadow-cljs watch app
```
    
## Testing

To test backend, run:

``` sh
lein test
```
You can find the tests in `test/clj/scrambles/main` folder.

To test frontend, run:

``` sh
rm -rf target/cljsbuild/public/js
shadow-cljs release app --config-merge "{:closure-defines {scramblies.main.app/CYPRESS? true}}"

npx cypress run --config video=false
```
This compiles ClojureScript files with all optimizations, then tests all its functionality. 
You can find the frontend test in `cypress/integration/landing/scramble.cljs`

## License

Copyright © 2021 FIXME
