document.addEventListener('DOMContentLoaded', function () {
    const modal = document.getElementById('pacienteModal');
    if (!modal) {
        return;
    }
    const form = document.getElementById('pacienteForm');
    const header = document.getElementById('pacienteModalHeader');
    const titulo = document.getElementById('pacienteModalTitulo');
    const id = document.getElementById('pacienteId');
    const nombre = document.getElementById('pacienteNombre');
    const apellidos = document.getElementById('pacienteApellidos');
    const cedula = document.getElementById('pacienteCedula');
    const correo = document.getElementById('pacienteCorreo');
    const telefono = document.getElementById('pacienteTelefono');
    const activo = document.getElementById('pacienteActivo');

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
            nombre.value = button.getAttribute('data-nombre') || '';
            apellidos.value = button.getAttribute('data-apellidos') || '';
            cedula.value = button.getAttribute('data-cedula') || '';
            correo.value = button.getAttribute('data-correo') || '';
            telefono.value = button.getAttribute('data-telefono') || '';
            activo.checked = button.getAttribute('data-activo') === 'true';
            header.classList.add('bg-warning');
        } else {
            activo.checked = true;
            header.classList.add('bg-info', 'text-white');
        }
    });
});
