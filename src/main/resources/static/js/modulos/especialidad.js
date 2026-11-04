document.addEventListener('DOMContentLoaded', function () {
    const modal = document.getElementById('especialidadModal');
    if (!modal) {
        return;
    }
    const form = document.getElementById('especialidadForm');
    const header = document.getElementById('especialidadModalHeader');
    const titulo = document.getElementById('especialidadModalTitulo');
    const id = document.getElementById('especialidadId');
    const descripcion = document.getElementById('especialidadDescripcion');
    const activo = document.getElementById('especialidadActivo');

    modal.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        if (!button) {
            return;
        }
        const modo = button.getAttribute('data-modo');
        form.reset();
        id.value = '';
        titulo.textContent = button.getAttribute('data-titulo') || '';
        header.classList.remove('bg-info', 'bg-warning', 'text-white');
        if (modo === 'editar') {
            id.value = button.getAttribute('data-id') || '';
            descripcion.value = button.getAttribute('data-descripcion') || '';
            activo.checked = button.getAttribute('data-activo') === 'true';
            header.classList.add('bg-warning');
        } else {
            activo.checked = true;
            header.classList.add('bg-info', 'text-white');
        }
    });
});
