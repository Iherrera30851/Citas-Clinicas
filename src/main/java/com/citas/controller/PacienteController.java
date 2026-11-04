package com.citas.controller;

import com.citas.domain.Paciente;
import com.citas.service.PacienteService;
import jakarta.validation.Valid;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/paciente")
public class PacienteController {

    private final PacienteService pacienteService;
    private final MessageSource messageSource;

    @GetMapping("/listado")
    public String listado(Model model) {
        var pacientes = pacienteService.getPacientes(false);
        model.addAttribute("pacientes", pacientes);
        model.addAttribute("totalPacientes", pacientes.size());
        return "paciente/listado";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Paciente paciente, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String mensaje = "mensaje.actualizado";
        if (bindingResult.hasErrors()) {
            titulo = "error";
            mensaje = "paciente.error04";
        } else {
            try {
                pacienteService.save(paciente);
            } catch (DataIntegrityViolationException e) {
                titulo = "error";
                mensaje = "paciente.error05";
            }
        }
        redirectAttributes.addFlashAttribute(titulo, messageSource.getMessage(mensaje, null, Locale.getDefault()));
        return "redirect:/paciente/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idPaciente, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String mensaje = "mensaje.eliminado";
        try {
            pacienteService.delete(idPaciente);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            mensaje = "paciente.error01";
        } catch (IllegalStateException e) {
            titulo = "error";
            mensaje = "paciente.error02";
        } catch (Exception e) {
            titulo = "error";
            mensaje = "paciente.error03";
        }
        redirectAttributes.addFlashAttribute(titulo, messageSource.getMessage(mensaje, null, Locale.getDefault()));
        return "redirect:/paciente/listado";
    }
}
