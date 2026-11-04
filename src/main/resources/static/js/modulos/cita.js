document.addEventListener('DOMContentLoaded', function () {
    const agendarModal = document.getElementById('agendarModal');
    if (agendarModal) {
        agendarModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            document.getElementById('agendarDisponibilidad').value = button.getAttribute('data-id');
            document.getElementById('agendarDescripcion').textContent = button.getAttribute('data-descripcion');
            document.getElementById('agendarMotivo').value = '';
        });
    }
    const reagendarModal = document.getElementById('reagendarModal');
    if (reagendarModal) {
        reagendarModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            document.getElementById('reagendarId').value = button.getAttribute('data-id');
            document.getElementById('reagendarDescripcion').textContent = button.getAttribute('data-descripcion');
        });
    }
});
