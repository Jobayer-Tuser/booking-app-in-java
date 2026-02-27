$(function () {
    $(".editCatBtn").on("click", function (event){
        event.preventDefault();
        let url = $(this).attr('href');
        $.get(url, function (category, status) {
            $("#editId").val(category.id);
            $("#editCatName").val(category.name);
        });
        $("#editCategoryModal").modal("show");
    })
})