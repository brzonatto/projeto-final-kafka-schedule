package br.com.dbc.pokedex.scheduler;

import br.com.dbc.pokedex.service.PokedexService;
import br.com.dbc.pokedex.service.ResumoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class Rotinas {
    private final ResumoService resumoService;

    @Scheduled(cron = "* 0 0 * * *")
    public void salvarResumoPokedex() {
        resumoService.salvarResumoPokedex();
    }

    @Scheduled(cron = "* 0 8 * * *")
    public void enviarResumoPokedex() throws JsonProcessingException {
        resumoService.enviarResumoPokedex();
    }


}
