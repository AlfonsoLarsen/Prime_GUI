import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.{Button, CheckBox, Label, TextField}
import scalafx.scene.layout.{HBox, VBox}

object Main extends JFXApp {
  def primeFactors(n: Int): List[Int] = {
    def factors(n: Int, i: Int): List[Int] =
      if (n <= 1) List.empty
      else if (i * i > n) List(n)
      else if (n % i == 0) i :: factors(n / i, i)
      else factors(n, i + 1)

    factors(n, 2)
  }

  stage = new PrimaryStage {
    title = "Descomposición en Factores Primos"
    width = 400
    height = 250

    val inputField = new TextField()
    val resultLabel = new Label()

    val uniqueFactorsCheckBox = new CheckBox("Mostrar factores primos únicos")
    val fullDecompositionCheckBox = new CheckBox("Mostrar descomposición completa")

    val calculateButton = new Button("Calcular")
    calculateButton.onAction = _ => {
      val input = inputField.getText.toInt
      val factors = primeFactors(input)

      val uniqueFactors = uniqueFactorsCheckBox.isSelected
      val fullDecomposition = fullDecompositionCheckBox.isSelected

      val resultFactors =
        if (uniqueFactors) factors.distinct else factors

      val result =
        if (uniqueFactors) s"${input} => (${resultFactors.mkString(", ")})"
        else if (fullDecomposition) resultFactors.mkString(" × ")
        else s"${input} = ${resultFactors.mkString(" × ")}"
      
      resultLabel.text = result
    }

    val inputBox = new VBox {
      spacing = 10
      padding = Insets(20)
      children = List(
        new HBox {
          spacing = 10
          children = List(new Label("Número:"), inputField, calculateButton)
        },
        uniqueFactorsCheckBox,
        fullDecompositionCheckBox,
        new HBox {
          spacing = 10
          children = List(new Label("Resultado:"), resultLabel)
        }
      )
    }

    scene = new Scene(inputBox)
  }
}
