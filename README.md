![](https://clojars.org/thought2/common-imgs/latest-version.svg)

![](https://raw.githubusercontent.com/thought2/common-imgs/master/logo/common-imgs-logo.png)
# common-imgs

Clojure library to retrieve random images from the Wikimedia Commons API.

## Usage

Add `[thought2/common-imgs "RELEASE"]` to your dependencies.

```clj
(require '[thought2.common-imgs :refer [random-img-specs img-spec->url])

(def img-specs (random-img-specs 50))
```

This returns a list of around 50 image specs, which look like this:


```clj
(def img-spec (first img-specs))

{:size [999 749],
 :url-template ["https://upload.wikimedia.org/wikipedia/commons/thumb/5/55
/Cuban_Embassy_in_Wellington.jpg/"
                "px-Cuban_Embassy_in_Wellington.jpg"]}
```

When this spec is passed to `img-spec->url` including a width parameter, one will get back an url which can be used to download the image in the right size:

```clj
(img-spec->url img-spec 100)
;; => 
"https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/Cuban_Embas
sy_in_Wellington.jpg/500px-Cuban_Embassy_in_Wellington.jpg"
```

`(img-spec->url img-spec 100)`

![](https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/Cuban_Embassy_in_Wellington.jpg/100px-Cuban_Embassy_in_Wellington.jpg)


`(img-spec->url img-spec 200)`

![](https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/Cuban_Embassy_in_Wellington.jpg/200px-Cuban_Embassy_in_Wellington.jpg)


`(img-spec->url img-spec 300)`

![](https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/Cuban_Embassy_in_Wellington.jpg/300px-Cuban_Embassy_in_Wellington.jpg)

