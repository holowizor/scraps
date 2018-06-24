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
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.0/css/all.css" integrity="sha384-lKuwvrZot6UHsBSfcMvOkWwlCMgc0TaWr+30HWe3a4ltaBwTZhyTEggF5tJv8tbt" crossorigin="anonymous">
    <title>Scraps</title>
    <style>
        .btn-wrap {
            position: relative;
        }
        .btn-wrap:hover .context-btn {
            display: block;
        }
        .btn-wrap-top, .btn-wrap-btm {
            float: left;
            padding-right: 10px;
        }
        .btn-wrap a {
            color: #007bff !important;
        }
        .btn-wrap a:hover {
            color: #dc3545 !important;
        }
        .context-btn {
            display: none;
        }
        .context-edit {
            position: absolute;
            top: 30px;
            left: 0px;
        }
        .context-delete {
            position: absolute;
            top: 30px;
            left: 20px;
        }
        .scrap-edit {
            position: absolute;
            top: 30px;
            left: 10px;
        }
        .scrap-delete {
            position: absolute;
            top: 30px;
            left: 30px;
        }
        /* bootbox dialog title fix */
        .bootbox .modal-header {
            display: block;
        }
    </style>

  </head>
  <body>
    <div class="navbar navbar-expand-md navbar-dark bg-dark">
      <span class="navbar-brand">Scraps</span>
      <div id="contexts" class="clearfix"></div>
    </div>
    <div class="clearfix"></div>
    <div id="scraps" class="clearfix"></div>
    <hr/>
    <div class="container-fluid">
      <div class="editable" style="outline: none;">Edit me!</div>
    </div>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <!-- script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script -->
    <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/medium-editor@latest/dist/js/medium-editor.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootbox.js/4.4.0/bootbox.min.js"></script>

    <script>
        var editor = new MediumEditor('.editable');

        var activeContextId = 0;
        var activeScrapId = 0;
        var contexts = [];
        var scraps = [];

        loadContexts();
        window.setInterval(saveActiveScrap, 15000);

        function getContext(id) {
            for (var i = 0; i < contexts.length; i++) {
                var context = contexts[i];
                if (context.id == id) return context;
            }
            return null;
        }

        function getScrap(id) {
            for (var i = 0; i < scraps.length; i++) {
                var scrap = scraps[i];
                if (scrap.id == id) return scrap;
            }
            return null;
        }

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
                    '<div class="btn-wrap btn-wrap-top"><button type="button" class="btn btn-sm btn-outline-secondary btn-context-select' + activeClass + '" data-toggle="button" data-context-id="' + context.id + '">' + context.name + '</button>' +
                    '<a class="context-edit context-btn" data-context-id="' + context.id + '"><i class="fas fa-edit"></i></a> <a class="context-delete context-btn" data-context-id="' + context.id + '"><i class="fas fa-trash"></i></a>' +
                    '</div>');
            }
            $('#contexts').append('<div class="btn-wrap btn-wrap-top"><button id="btn-new-context" type="button" class="btn btn-sm btn-outline-secondary">New</button></div>');

            $('.btn-context-select').click(function() {
                selectContext($(this).data('context-id'));
            });

            $('#btn-new-context').click(function() {
                bootbox.prompt("Enter new context name", function(name) {
                    if (name == null || name.trim() == '') return;

                    var newContext = {
                        id: 0,
                        name: name.trim()
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
            });

            $('.context-edit').click(function() {
                var context = getContext($(this).data('context-id'));
                bootbox.prompt({title: "Change context name", value: context.name, callback: function(name) {
                    if (name == null || name.trim() == '') return;
                    context.name = name;
                    $.ajax({
                        url: '/context',
                        type: 'put',
                        dataType: 'json',
                        contentType: 'application/json',
                        success: function (data) {
                            renderContexts();
                        },
                        data: JSON.stringify(context)
                    });
                }});
            });

            $('.context-delete').click(function() {
                var context = getContext($(this).data('context-id'));
                bootbox.confirm('Remove context "' + context.name + '"?', function(yes) {
                    if (yes == false) return;
                    if (activeContextId == context.id) activeContextId = null;
                    $.ajax({
                        url: '/context/' + context.id,
                        type: 'delete',
                        dataType: 'json',
                        contentType: 'application/json',
                        success: function (data) {
                            contexts = data;
                            var loadScraps = false;
                            if (activeContextId == null) {
                                activeContextId = data[0].id;
                                loadScraps = true;
                            }
                            renderContexts();
                            if (loadScraps) loadScraps(activeContextId);
                        }
                    });
                });
            });
        }

        function selectContext(contextId) {
            if (contextId == activeContextId) return;
            saveActiveScrap();

            $("button[data-context-id='" + activeContextId + "']").removeClass('active');
            activeContextId = contextId;
            loadScraps(activeContextId);
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
                    '<div class="btn-wrap btn-wrap-btm"><button type="button" class="btn btn-sm btn-link btn-scrap-select' + activeClass + '" data-scrap-id="' + scrap.id + '">' + scrap.name + '</button>' +
                    '<a class="scrap-edit context-btn" data-scrap-id="' + scrap.id + '"><i class="fas fa-edit"></i></a> <a class="scrap-delete context-btn" data-scrap-id="' + scrap.id + '"><i class="fas fa-trash"></i></a>' +
                    '</div>');
            }
            $('#scraps').append('<div class="btn-wrap btn-wrap-btm"><button id="btn-new-scrap" type="button" class="btn btn-sm btn-link">New</button></div>');

            $('.btn-scrap-select').click(function() {
                selectScrap($(this).data('scrap-id'));
            });

            $('#btn-new-scrap').click(function() {
                bootbox.prompt("Enter new scrap title", function(title) {
                    if (title == null || title.trim() == '') return;

                    var newScrap = {
                        id: 0,
                        contextId: activeContextId,
                        name: title.trim(),
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
            });

            $('.scrap-edit').click(function() {
                var scrap = getScrap($(this).data('scrap-id'));
                bootbox.prompt({title: "Change scrap title", value: scrap.name, callback: function(title) {
                    if (title == null || title.trim() == '') return;
                    scrap.name = title;
                    scrap.body = editor.getContent(0);
                    $.ajax({
                        url: '/context/' + activeContextId + '/scrap',
                        type: 'put',
                        dataType: 'json',
                        contentType: 'application/json',
                        success: function (data) {
                            renderScraps();
                        },
                        data: JSON.stringify(scrap)
                    });
                }});
            });

            $('.scrap-delete').click(function() {
                var scrap = getScrap($(this).data('scrap-id'));
                bootbox.confirm('Remove scrap "' + scrap.name + '"?', function(yes) {
                    if (yes == false) return;
                    if (activeScrapId == scrap.id) activeScrapId = null;
                    $.ajax({
                        url: '/context/' + activeContextId + '/scrap/' + scrap.id,
                        type: 'delete',
                        dataType: 'json',
                        contentType: 'application/json',
                        success: function (data) {
                            scraps = data;
                            var loadScraps = false;
                            if (activeScrapId == null) {
                                activeScrapId = data[0].id;
                            }
                            renderScraps();
                        }
                    });
                });
            });
        }

        function selectScrap(scrapId) {
            if (scrapId == activeScrapId) return;
            saveActiveScrap();

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

    </script>
  </body>
</html>
