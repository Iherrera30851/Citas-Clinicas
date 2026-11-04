document.addEventListener('DOMContentLoaded', function () {
    const notaModal = document.getElementById('notaModal');
    if (!notaModal) {
        return;
    }
    notaModal.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        document.getElementById('notaCita').value = button.getAttribute('data-id');
        document.getElementById('notaPaciente').textContent = button.getAttribute('data-descripcion');
        document.getElementById('notaDescripcion').value = button.getAttribute('data-nota') || '';
    });
});
