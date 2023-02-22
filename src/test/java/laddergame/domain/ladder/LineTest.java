package laddergame.domain.ladder;

import laddergame.domain.rung.Rung;
import laddergame.domain.rung.RungBooleanGenerator;
import laddergame.util.BooleanGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LineTest {

    private BooleanGenerator rungBooleanGenerator;

    @BeforeEach
    void init() {
        rungBooleanGenerator = new RungBooleanGenerator();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    @DisplayName("rungCount와 numberGenerator를 입력하면, 객체가 생성되는지 확인한다.")
    void succeeds_creation_if_rung_count_and_number_generator_are_entered(int rungCount) {
        assertThat(Line.create(rungCount, rungBooleanGenerator))
                .isInstanceOf(Line.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    @DisplayName("객체의 사이즈가 rungCount와 같은지 확인한다.")
    void is_same_size_as_rung_count(int rungCount) {
        // when
        Line line = Line.create(rungCount, rungBooleanGenerator);
        List<Rung> specificRungs = line.getRungs();

        // then
        assertThat(specificRungs.size()).isEqualTo(rungCount);
    }

    @ParameterizedTest
    @MethodSource("getTestRungsWithBooleanGenerator")
    @DisplayName("객체의 형태가 BooleanGenerator에 따라 달라지는지 확인한다.")
    void creates_various_rungs_according_to_boolean_generator(int rungCount, BooleanGenerator booleanGenerator, List<Rung> expectedRungs) {
        // given
        Line line = Line.create(rungCount, booleanGenerator);

        // when
        List<Rung> actualRungs = line.getRungs();

        // then
        assertThat(actualRungs).isEqualTo(expectedRungs);
    }

    private static Stream<Arguments> getTestRungsWithBooleanGenerator() {
        final BooleanGenerator sufficientMaterialGenerator = () -> true;
        final BooleanGenerator insufficientMaterialGenerator = () -> false;
        final boolean canMakeRung = true;
        final boolean canNotMakeRung = !canMakeRung;
        return Stream.of(
                Arguments.arguments(1, sufficientMaterialGenerator, List.of(Rung.create(canMakeRung))),
                Arguments.arguments(2, sufficientMaterialGenerator, List.of(Rung.create(canMakeRung), Rung.create(canNotMakeRung))),
                Arguments.arguments(3, sufficientMaterialGenerator, List.of(Rung.create(canMakeRung), Rung.create(canNotMakeRung), Rung.create(canMakeRung))),
                Arguments.arguments(4, sufficientMaterialGenerator, List.of(Rung.create(canMakeRung), Rung.create(canNotMakeRung), Rung.create(canMakeRung), Rung.create(canNotMakeRung))),
                Arguments.arguments(1, insufficientMaterialGenerator, List.of(Rung.create(canNotMakeRung))),
                Arguments.arguments(2, insufficientMaterialGenerator, List.of(Rung.create(canNotMakeRung), Rung.create(canNotMakeRung))),
                Arguments.arguments(3, insufficientMaterialGenerator, List.of(Rung.create(canNotMakeRung), Rung.create(canNotMakeRung), Rung.create(canNotMakeRung))),
                Arguments.arguments(4, insufficientMaterialGenerator, List.of(Rung.create(canNotMakeRung), Rung.create(canNotMakeRung), Rung.create(canNotMakeRung), Rung.create(canNotMakeRung)))
        );
    }
}