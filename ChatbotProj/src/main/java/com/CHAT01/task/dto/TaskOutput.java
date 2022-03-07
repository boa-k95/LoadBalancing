package task.dto;

import lombok.Data;
import com.CHAT01.model.ActiveContext;
import com.CHAT01.model.Event;
import com.CHAT01.model.OutputParameters;

import java.util.Objects;
import java.util.Optional;

@Data
public class TaskOutput {
    /* Informazioni estratte passate in input al servizio,
     *  PER REPORTISTICA */
    private TaskInput taskInput;

    /* Testo per l'utente */
    private Optional<String> text;
    /**
     * Ulteriori parametri da restituire all'app insieme al testo per l'utente (ad esempio i parametri per la creazione di un link)
     */
    private OutputParameters outputParameters;
    /* Evento completo per Dialogflow */
    private Optional<Event> event;

    private Optional<ActiveContext> context;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskOutput)) return false;
        TaskOutput that = (TaskOutput) o;
        return Objects.equals(getTaskInput(), that.getTaskInput()) && Objects.equals(getText(), that.getText()) && Objects.equals(getOutputParameters(), that.getOutputParameters()) && Objects.equals(getEvent(), that.getEvent()) && Objects.equals(getContext(), that.getContext());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTaskInput(), getText(), getOutputParameters(), getEvent(), getContext());
    }
}
