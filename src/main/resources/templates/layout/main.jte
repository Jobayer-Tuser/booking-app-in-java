@param gg.jte.Content content

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="description" content="" />
    <meta name="author" content="Jobayer Al Mahmud" />
    <link href="/static/assets/css/vendor.min.css" rel="stylesheet" />
    <link href="/static/assets/css/app.min.css" rel="stylesheet" />
    <link href="/static/assets/plugins/summernote/dist/summernote-lite.css" rel="stylesheet"/>
    <script rel="stylesheet" src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
    <title>Categories</title>
</head>
<body>
<div id="app" class="app">
    @template.components.topbar()
    @template.components.menubar()
    <div>${content}</div>
    <div> Hello tthere</div>
    @template.components.settings()
</div>

</body>
<footer>
    <script type="text/javascript" src="/static/assets/js/vendor.min.js" ></script>
    <script type="text/javascript" src="/static/assets/js/app.min.js" ></script>
    <script type="text/javascript" src="/static/assets/js/custom/categories.js" ></script>
    <script type="text/javascript" src="/static/assets/plugins/highlight.js/highlight.min.js" ></script>
    <script type="text/javascript" src="/static/assets/js/demo/highlightjs.demo.js" ></script>
    <script type="text/javascript" src="/static/assets/js/demo/ui-modal-notification.demo.js" ></script>
    <script type="text/javascript" src="/static/assets/plugins/summernote/dist/summernote-lite.min.js"></script>

    <script type="text/javascript">
        $(document).ready(function() {
            $('.summernote').summernote({
                placeholder: 'Product Description',
                height: 200,
                tabsize: 2,
            });
        });
    </script>
</footer>
</html>