document.addEventListener('DOMContentLoaded', function () {
    const modal = document.getElementById('disponibilidadModal');
    if (!modal) {
        return;
    }
    const form = document.getElementById('disponibilidadForm');
    const header = document.getElementById('disponibilidadModalHeader');
    const titulo = document.getElementById('disponibilidadModalTitulo');
    const id = document.getElementById('disponibilidadId');
    const profesional = document.getElementById('disponibilidadProfesional');
    const fecha = document.getElementById('disponibilidadFecha');
    const horaInicio = document.getElementById('disponibilidadHoraInicio');
    const horaFin = document.getElementById('disponibilidadHoraFin');

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
            profesional.value = button.getAttribute('data-profesional-id') || '';
            fecha.value = button.getAttribute('data-fecha') || '';
            horaInicio.value = button.getAttribute('data-hora-inicio') || '';
            horaFin.value = button.getAttribute('data-hora-fin') || '';
            header.classList.add('bg-warning');
        } else {
            if (profesional.options.length > 0) {
                profesional.selectedIndex = 0;
            }
            header.classList.add('bg-info', 'text-white');
        }
    });
});
