package io.github.saimonovski.smv.wallet.cloud;


import org.bukkit.command.CommandSender;
import org.incendo.cloud.caption.CaptionProvider;
import org.incendo.cloud.caption.StandardCaptionKeys;
import org.incendo.cloud.paper.LegacyPaperCommandManager;

public class CaptionRegister {
        public static void registerStandard(LegacyPaperCommandManager<CommandSender> manager) {
            manager.captionRegistry()
                    .registerProvider(CaptionProvider.constantProvider(
                            StandardCaptionKeys.ARGUMENT_PARSE_FAILURE_BOOLEAN,
                            "\"<input>\" nie jest prawidłową wartością logiczną"
                    ))
                    .registerProvider(CaptionProvider.constantProvider(
                            StandardCaptionKeys.ARGUMENT_PARSE_FAILURE_NUMBER,
                            "\"<input>\" nie jest prawidłową wartością liczbową"
                    ))
                    .registerProvider(CaptionProvider.constantProvider(
                            StandardCaptionKeys.ARGUMENT_PARSE_FAILURE_CHAR,
                            "\"<input>\" nie jest prawidłowym znakiem"
                    ))
                    .registerProvider(CaptionProvider.constantProvider(
                            StandardCaptionKeys.ARGUMENT_PARSE_FAILURE_STRING,
                            "\"<input>\" nie jest prawidłowym ciągiem"
                    ))
                    .registerProvider(CaptionProvider.constantProvider(
                            StandardCaptionKeys.ARGUMENT_PARSE_FAILURE_UUID,
                            "\"<input>\" nie jest prawidłowym UUID"
                    ))
                    .registerProvider(CaptionProvider.constantProvider(
                            StandardCaptionKeys.ARGUMENT_PARSE_FAILURE_ENUM,
                            "\"<input>\" nie jest prawidłową wartością typu wyliczeniowego"
                    ))
                    .registerProvider(CaptionProvider.constantProvider(
                            StandardCaptionKeys.ARGUMENT_PARSE_FAILURE_REGEX,
                            "\"<input>\" nie pasuje do wzoru"
                    ))
                    .registerProvider(CaptionProvider.constantProvider(
                            StandardCaptionKeys.ARGUMENT_PARSE_FAILURE_FLAG_UNKNOWN_FLAG,
                            "Nieznany flaga \"<input>\""
                    ))
                    .registerProvider(CaptionProvider.constantProvider(
                            StandardCaptionKeys.ARGUMENT_PARSE_FAILURE_FLAG_DUPLICATE_FLAG,
                            "Flaga \"<input>\" jest zduplikowana"
                    ))
                    .registerProvider(CaptionProvider.constantProvider(
                            StandardCaptionKeys.ARGUMENT_PARSE_FAILURE_FLAG_NO_FLAG_STARTED,
                            "Nie rozpoczęto żadnej flagi"
                    ))
                    .registerProvider(CaptionProvider.constantProvider(
                            StandardCaptionKeys.ARGUMENT_PARSE_FAILURE_FLAG_MISSING_ARGUMENT,
                            "Brak argumentu dla flagi \"<input>\""
                    ))
                    .registerProvider(CaptionProvider.constantProvider(
                            StandardCaptionKeys.ARGUMENT_PARSE_FAILURE_FLAG_NO_PERMISSION,
                            "Brak uprawnień dla flagi \"<input>\""
                    ))
                    .registerProvider(CaptionProvider.constantProvider(
                            StandardCaptionKeys.ARGUMENT_PARSE_FAILURE_COLOR,
                            "\"<input>\" nie jest prawidłowym kolorem"
                    ))
                    .registerProvider(CaptionProvider.constantProvider(
                            StandardCaptionKeys.ARGUMENT_PARSE_FAILURE_DURATION,
                            "\"<input>\" nie jest prawidłową długością"
                    ))
                    .registerProvider(CaptionProvider.constantProvider(
                            StandardCaptionKeys.ARGUMENT_PARSE_FAILURE_AGGREGATE_MISSING_INPUT,
                            "Brak wymaganych danych wejściowych"
                    ))
                    .registerProvider(CaptionProvider.constantProvider(
                            StandardCaptionKeys.ARGUMENT_PARSE_FAILURE_AGGREGATE_COMPONENT_FAILURE,
                            "Przy próbie parsowania komponentu wystąpił błąd"
                    ))
                    .registerProvider(CaptionProvider.constantProvider(
                            StandardCaptionKeys.ARGUMENT_PARSE_FAILURE_EITHER,
                            "Nieprawidłowe wejście. Oczekiwano jedno z: <options>"
                    ))
                    .registerProvider(CaptionProvider.constantProvider(
                            StandardCaptionKeys.EXCEPTION_UNEXPECTED,
                            "Wystąpił niespodziewany błąd"
                    ))
                    .registerProvider(CaptionProvider.constantProvider(
                            StandardCaptionKeys.EXCEPTION_INVALID_ARGUMENT,
                            "Nieprawidłowy argument"
                    ))
                    .registerProvider(CaptionProvider.constantProvider(
                            StandardCaptionKeys.EXCEPTION_NO_SUCH_COMMAND,
                            "Nieznana komenda"
                    ))
                    .registerProvider(CaptionProvider.constantProvider(
                            StandardCaptionKeys.EXCEPTION_NO_PERMISSION,
                            "Brak uprawnień"
                    ))
                    .registerProvider(CaptionProvider.constantProvider(
                            StandardCaptionKeys.EXCEPTION_INVALID_SENDER,
                            "Nieprawidłowy nadawca"
                    ))
                    .registerProvider(CaptionProvider.constantProvider(
                            StandardCaptionKeys.EXCEPTION_INVALID_SENDER_LIST,
                            "Nieprawidłowa lista nadawców"
                    ))
                    .registerProvider(CaptionProvider.constantProvider(
                            StandardCaptionKeys.EXCEPTION_INVALID_SYNTAX,
                            "Nieprawidłowa składnia"
                    ));
        }
    }

