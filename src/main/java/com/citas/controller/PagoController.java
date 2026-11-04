package com.citas.controller;

import com.citas.domain.Pago;
import com.citas.service.CitaService;
import com.citas.service.PagoService;
import jakarta.validation.Valid;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pago")
public class PagoController {

    private final PagoService pagoService;
    private final CitaService citaService;
    private final MessageSource messageSource;

    @GetMapping("/listado")
    public String listado(Model model) {
        var pagos = pagoService.getPagos();
        model.addAttribute("pagos", pagos);
        model.addAttribute("totalPagos", pagos.size());
        model.addAttribute("citasPorPagar", citaService.getCitasPorPagar());
        return "pago/listado";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Pago pago, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String mensaje = "pago.registrado";
        if (bindingResult.hasErrors()) {
            titulo = "error";
            mensaje = "pago.error01";
        } else {
            try {
                pagoService.save(pago);
            } catch (IllegalArgumentException e) {
                titulo = "error";
                mensaje = "pago.error02";
            } catch (IllegalStateException e) {
                titulo = "error";
                mensaje = "pago.error02";
            }
        }
        redirectAttributes.addFlashAttribute(titulo, messageSource.getMessage(mensaje, null, Locale.getDefault()));
        return "redirect:/pago/listado";
    }
}
