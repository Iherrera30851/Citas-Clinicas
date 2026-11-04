document.addEventListener('DOMContentLoaded', function () {
    const modal = document.getElementById('profesionalModal');
    if (!modal) {
        return;
    }
    const form = document.getElementById('profesionalForm');
    const header = document.getElementById('profesionalModalHeader');
    const titulo = document.getElementById('profesionalModalTitulo');
    const id = document.getElementById('profesionalId');
    const especialidad = document.getElementById('profesionalEspecialidad');
    const nombre = document.getElementById('profesionalNombre');
    const apellidos = document.getElementById('profesionalApellidos');
    const cedula = document.getElementById('profesionalCedula');
    const correo = document.getElementById('profesionalCorreo');
    const telefono = document.getElementById('profesionalTelefono');
    const activo = document.getElementById('profesionalActivo');

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
            especialidad.value = button.getAttribute('data-especialidad-id') || '';
            nombre.value = button.getAttribute('data-nombre') || '';
            apellidos.value = button.getAttribute('data-apellidos') || '';
            cedula.value = button.getAttribute('data-cedula') || '';
            correo.value = button.getAttribute('data-correo') || '';
            telefono.value = button.getAttribute('data-telefono') || '';
            activo.checked = button.getAttribute('data-activo') === 'true';
            header.classList.add('bg-warning');
        } else {
            if (especialidad.options.length > 0) {
                especialidad.selectedIndex = 0;
            }
            activo.checked = true;
            header.classList.add('bg-info', 'text-white');
        }
    });
});
