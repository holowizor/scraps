<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    <link rel="stylesheet" href="//cdn.jsdelivr.net/npm/medium-editor@latest/dist/css/medium-editor.min.css" type="text/css" media="screen" charset="utf-8">
    <link rel="stylesheet" href="//cdn.jsdelivr.net/npm/medium-editor@latest/dist/css/themes/beagle.min.css" type="text/css" media="screen" charset="utf-8">
    <title>Hello, world!</title>
  </head>
  <body>
    <h1>Hello, ${context.name}!</h1>
    Your email address is ${context.id}
    <hr/>
    <div class="editable">Edit me!</div>
    <hr/>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
    <script src="//cdn.jsdelivr.net/npm/medium-editor@latest/dist/js/medium-editor.min.js"></script>

    <script>
        var editor = new MediumEditor('.editable');
        //editor.setContent('<p>hello</p>', 0);
        //var editorContents = editor.getContent(0);
        //alert(editorContents);

        var activeContextId = 0;
        var activeScrapId = 0;
        var contexts = [];
        var scraps = [];

        // general flow: load contexts, activate first, get its scraps, activate first
        function loadContexts() {

        }

        function renderContexts() {

        }

        function selectContext(contextId) {

        }

        function loadScraps(contextId) {

        }

        function selectScrap(scrapId) {

        }

        function newContext() {

        }

        function newScrap() {

        }
    </script>
  </body>
</html>
