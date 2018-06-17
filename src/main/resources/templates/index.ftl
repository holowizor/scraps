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
    <div id="contexts"></div>
    <div id="scraps"></div>
    <h1>Hello, ${context.name}!</h1>
    Your email address is ${context.id}
    <hr/>
    <div class="editable">Edit me!</div>
    <hr/>

<div class="modal fade" id="newContextModal" tabindex="-1" role="dialog" aria-labelledby="newContextModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="newContextModalLabel">New context</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form>
          <div class="form-group">
            <label for="recipient-name" class="col-form-label">Name:</label>
            <input type="text" class="form-control" id="new-context-name">
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
        <button type="button" class="btn btn-primary" id="btn-create-context">Create</button>
      </div>
    </div>
  </div>
</div>

<div class="modal fade" id="newScrapModal" tabindex="-1" role="dialog" aria-labelledby="newScrapModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="newScrapModalLabel">New scrap</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form>
          <div class="form-group">
            <label for="recipient-name" class="col-form-label">Name:</label>
            <input type="text" class="form-control" id="new-scrap-name">
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
        <button type="button" class="btn btn-primary" id="btn-create-scrap">Create</button>
      </div>
    </div>
  </div>
</div>


    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <!-- script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script -->
    <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
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

        loadContexts();
        initDialogs();
        window.setInterval(saveActiveScrap, 1500);

        // general flow: load contexts, activate first, get its scraps, activate first
        function loadContexts() {
            $.getJSON('/context', function(data) {
                contexts = data;
                activeContextId = data[0].id;

                renderContexts();
                loadScraps(activeContextId);
            });
        }

        function renderContexts() {
            $('#contexts').html('');
            for (var i = 0; i < contexts.length; i++) {
                var context = contexts[i];
                var active = (activeContextId == context.id);
                var activeClass = '';
                if (active) activeClass = ' active';

                $("#contexts").append(
                    '<button type="button" class="btn btn-sm btn-outline-secondary btn-context-select' + activeClass + '" data-toggle="button" data-context-id="' + context.id + '">' + context.name + '</button> ');
                // TODO add edit + delete icons (shown when holding mouse over for a bit longer?)
            }
            $('#contexts').append('<button id="btn-new-context" type="button" class="btn btn-sm btn-outline-secondary">New</button>');

            $('.btn-context-select').click(function() {
                selectContext($(this).data('context-id'));
            });

            $('#btn-new-context').click(function() {
                newContext();
            });
        }

        function selectContext(contextId) {
            if (contextId == activeContextId) return;
            $("button[data-context-id='" + activeContextId + "']").removeClass('active');
            activeContextId = contextId;
            loadScraps(activeContextId);
        }

        function newContext() {
            //
        }

        function loadScraps(contextId) {
            $.getJSON('/context/' + activeContextId + '/scrap', function(data) {
                scraps = data;
                activeScrapId = data[0].id;

                renderScraps();
            });
        }

        function renderScraps() {
            $('#scraps').html('');
            for (var i = 0; i < scraps.length; i++) {
                var scrap = scraps[i];
                var active = (activeScrapId == scrap.id);
                var activeClass = '';
                if (active) {
                    activeClass = ' font-weight-bold';
                    editor.setContent(scrap.body, 0);
                }

                $("#scraps").append(
                    '<button type="button" class="btn btn-sm btn-link btn-scrap-select' + activeClass + '" data-scrap-id="' + scrap.id + '">' + scrap.name + '</button> ');
                // TODO add edit + delete icons (shown when holding mouse over for a bit longer?)
            }
            $('#scraps').append('<button id="btn-new-scrap" type="button" class="btn btn-sm btn-link">New</button>');

            $('.btn-scrap-select').click(function() {
                selectScrap($(this).data('scrap-id'));
            });

            $('#btn-new-scrap').click(function() {
                newScrap();
            });
        }

        function selectScrap(scrapId) {
            if (scrapId == activeScrapId) return;
            // FIXME save current (just in case)

            $("button[data-scrap-id='" + activeScrapId + "']").removeClass('font-weight-bold');
            $("button[data-scrap-id='" + scrapId + "']").addClass('font-weight-bold');
            activeScrapId = scrapId;

            for (var i = 0; i < scraps.length; i++) {
                var scrap = scraps[i];
                if (activeScrapId == scrap.id) {
                    editor.setContent(scrap.body, 0);
                }
            }
        }

        function initDialogs() {
            $('#btn-create-context').click(function() {
                $('#newContextModal').modal('hide');
                var newContext = {
                    id: 0,
                    name:$("#new-context-name").val()
                }
                $.ajax({
                    url: '/context',
                    type: 'post',
                    dataType: 'json',
                    contentType: 'application/json',
                    success: function (data) {
                        contexts.push(data);
                        renderContexts();
                    },
                    data: JSON.stringify(newContext)
                });
            });

            $('#btn-create-scrap').click(function() {
                $('#newScrapModal').modal('hide');
                var newScrap = {
                    id: 0,
                    contextId: activeContextId,
                    name: $("#new-scrap-name").val(),
                    body: ''
                }
                $.ajax({
                    url: '/context/' + activeContextId + '/scrap',
                    type: 'post',
                    dataType: 'json',
                    contentType: 'application/json',
                    success: function (data) {
                        scraps.push(data);
                        renderScraps();
                    },
                    data: JSON.stringify(newScrap)
                });

            });
        }

        function newContext() {
            $('#newContextModal').modal('show');
        }

        function newScrap() {
            $('#newScrapModal').modal('show');
        }

        function saveActiveScrap() {
            if (activeContextId == 0 || activeScrapId == 0) return;

            var i, scrap;
            for (i=0; i<scraps.length; i++) {
                if (activeScrapId == scraps[i].id) {
                    scrap = scraps[i];
                    break;
                }
            }

            scrap.body = editor.getContent(0);
            $.ajax({
                url: '/context/' + activeContextId + '/scrap',
                type: 'put',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(scrap)
            });
        }

        // FIXME set timer, to 10s to save content (and update scraps table - body)
    </script>
  </body>
</html>
