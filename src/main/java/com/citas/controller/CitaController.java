package com.citas.controller;

import com.citas.domain.Cita;
import com.citas.service.CitaService;
import com.citas.service.DisponibilidadService;
import com.citas.service.EspecialidadService;
import com.citas.service.PacienteService;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cita")
public class CitaController {

    private final CitaService citaService;
    private final DisponibilidadService disponibilidadService;
    private final EspecialidadService especialidadService;
    private final PacienteService pacienteService;
    private final MessageSource messageSource;

    @GetMapping("/agendar")
    public String agendar(Model model) {
        model.addAttribute("libres", disponibilidadService.getLibres(null));
        model.addAttribute("especialidades", especialidadService.getEspecialidades(true));
        model.addAttribute("pacientes", pacienteService.getPacientes(true));
        return "cita/agendar";
    }

    @GetMapping("/agendar/{idEspecialidad}")
    public String agendarPorEspecialidad(@PathVariable("idEspecialidad") Integer idEspecialidad, Model model) {
        model.addAttribute("idEspecialidadActual", idEspecialidad);
        model.addAttribute("libres", disponibilidadService.getLibres(idEspecialidad));
        model.addAttribute("especialidades", especialidadService.getEspecialidades(true));
        model.addAttribute("pacientes", pacienteService.getPacientes(true));
        return "cita/agendar";
    }

    @PostMapping("/agendar")
    public String guardarCita(Cita cita, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String mensaje = "cita.agendada";
        try {
            citaService.agendar(cita);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            mensaje = "cita.error01";
        } catch (IllegalStateException e) {
            titulo = "error";
            mensaje = "cita.error02";
        }
        redirectAttributes.addFlashAttribute(titulo, messageSource.getMessage(mensaje, null, Locale.getDefault()));
        return "redirect:/cita/agendar";
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var citas = citaService.getCitas();
        model.addAttribute("citas", citas);
        model.addAttribute("totalCitas", citas.size());
        model.addAttribute("libres", disponibilidadService.getLibres(null));
        return "cita/listado";
    }

    @PostMapping("/estado")
    public String estado(@RequestParam Integer idCita, @RequestParam String estado, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String mensaje = "mensaje.actualizado";
        try {
            citaService.cambiarEstado(idCita, estado);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            mensaje = "cita.error01";
        } catch (IllegalStateException e) {
            titulo = "error";
            mensaje = "cita.error03";
        }
        redirectAttributes.addFlashAttribute(titulo, messageSource.getMessage(mensaje, null, Locale.getDefault()));
        return "redirect:/cita/listado";
    }

    @PostMapping("/reagendar")
    public String reagendar(@RequestParam Integer idCita, @RequestParam Integer idDisponibilidad, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String mensaje = "cita.reagendada";
        try {
            citaService.reagendar(idCita, idDisponibilidad);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            mensaje = "cita.error01";
        } catch (IllegalStateException e) {
            titulo = "error";
            mensaje = "cita.error02";
        }
        redirectAttributes.addFlashAttribute(titulo, messageSource.getMessage(mensaje, null, Locale.getDefault()));
        return "redirect:/cita/listado";
    }
}
