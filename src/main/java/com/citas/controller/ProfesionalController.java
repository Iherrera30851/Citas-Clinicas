package com.citas.controller;

import com.citas.domain.Profesional;
import com.citas.service.EspecialidadService;
import com.citas.service.ProfesionalService;
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
@RequestMapping("/profesional")
public class ProfesionalController {

    private final ProfesionalService profesionalService;
    private final EspecialidadService especialidadService;
    private final MessageSource messageSource;

    @GetMapping("/listado")
    public String listado(Model model) {
        var profesionales = profesionalService.getProfesionales(false);
        model.addAttribute("profesionales", profesionales);
        model.addAttribute("totalProfesionales", profesionales.size());
        model.addAttribute("especialidades", especialidadService.getEspecialidades(true));
        return "profesional/listado";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Profesional profesional, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String mensaje = "mensaje.actualizado";
        if (bindingResult.hasErrors()) {
            titulo = "error";
            mensaje = "profesional.error04";
        } else {
            try {
                profesionalService.save(profesional);
            } catch (DataIntegrityViolationException e) {
                titulo = "error";
                mensaje = "profesional.error05";
            }
        }
        redirectAttributes.addFlashAttribute(titulo, messageSource.getMessage(mensaje, null, Locale.getDefault()));
        return "redirect:/profesional/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idProfesional, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String mensaje = "mensaje.eliminado";
        try {
            profesionalService.delete(idProfesional);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            mensaje = "profesional.error01";
        } catch (IllegalStateException e) {
            titulo = "error";
            mensaje = "profesional.error02";
        } catch (Exception e) {
            titulo = "error";
            mensaje = "profesional.error03";
        }
        redirectAttributes.addFlashAttribute(titulo, messageSource.getMessage(mensaje, null, Locale.getDefault()));
        return "redirect:/profesional/listado";
    }
}
