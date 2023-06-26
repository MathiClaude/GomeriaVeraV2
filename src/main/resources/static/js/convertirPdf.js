
const button = document.getElementById("download-button");

      function generatePDF() {
		console.log('hola')
        // Choose the element that your content will be rendered to.
        const element = document.getElementById("impresion");
        // Choose the element and save the PDF for your user.
        html2pdf().from(element).save();
      }

      //button.addEventListener("click", generatePDF);