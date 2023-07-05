const button = document.getElementById("download-button");

function generatePDF() {
    console.log('hola');
    // Choose the element that your content will be rendered to.
    const element = document.getElementById("impresion");
    // Choose the element and save the PDF for your user.
    // Para los márgenes del PDF y números de paginación
    let opt = {
        // margin: 0.5,
        margin: [0.2, 0, 0.5, 0],
        filename: "reporteGomeria.pdf",
        image: { type: "jpeg", quality: 0.99 },
        jsPDF: { 
            unit: "in",
            format: "A4",
            orientation: "portrait",
            putOnlyUsedFonts: true,
            floatPrecision: 16,
            hotfixes: [] // optional array of hotfix strings to enable
        },
        pagebreak: { mode: ['avoid-all', 'css', 'legacy'], before: '.card', after: '.card' },
        html2canvas: { scale: 2 },
        // Para mostrar el número de página
        html2pdf: {
            onAfterPageContent: function (currentPage, totalPage) {
                const pageNumberText = 'Página ' + currentPage.toString() + ' de ' + totalPage;
                const jsPDFInstance = this.jsPDF;
                const pageWidth = jsPDFInstance.internal.pageSize.getWidth();
                const pageHeight = jsPDFInstance.internal.pageSize.getHeight();
                jsPDFInstance.setFontSize(10);
                jsPDFInstance.setTextColor(0, 0, 0);
                jsPDFInstance.text(pageNumberText, pageWidth - 15, pageHeight - 2);
            }
        }
    };
    // html2pdf().set(opt).from(element).save();
    html2pdf().from(element).set(opt).toPdf().get('pdf').then(function(pdf) {
        var totalPages = pdf.internal.getNumberOfPages();
        for (i = 1; i <= totalPages; i++) {
            pdf.setPage(i);
            pdf.setFontSize(10);
            pdf.setTextColor(100);
            pdf.text( i + ' / ' + totalPages, (pdf.internal.pageSize.getWidth() / 2.1), (pdf.internal.pageSize.getHeight() - 0.3));
        }
    }).save();
}
