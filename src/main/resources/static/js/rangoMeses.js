let $resetBtn, $datepickers;
$resetBtn = $("#resetBtn");
$datepickers = $(".datepicker");

$datepickers.datepicker({
    format: "mm/dd/yyyy",
    autoclose: true,
    todayHighlight: true,
    clearBtn: true
});

$resetBtn.on("click", function() {
    $datepickers.datepicker('update', ''); // Reset datepicker to today's date when shown
});