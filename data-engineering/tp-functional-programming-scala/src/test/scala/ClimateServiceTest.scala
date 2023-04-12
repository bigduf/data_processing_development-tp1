import com.github.polomarcus.utils.ClimateService
import com.github.polomarcus.model.CO2Record
import org.scalatest.funsuite.AnyFunSuite

//@See https://www.scalatest.org/scaladoc/3.1.2/org/scalatest/funsuite/AnyFunSuite.html
class ClimateServiceTest extends AnyFunSuite {
  test("containsWordGlobalWarming - non climate related words should return false") {
    assert(ClimateService.isClimateRelated("pizza") == false)
    assert(ClimateService.isClimateRelated("I want to eat") == false)
  }

  test("isClimateRelated - climate related words should return true") {
    assert(ClimateService.isClimateRelated("climate change") == true)
    assert(ClimateService.isClimateRelated("IPCC") == true)
  }

  //@TODO
  test("parseRawData") {
    // our inputs
    val firstRecord = (2003, 1, 355.2)     //help: to acces 2003 of this tuple, you can do firstRecord._1
    val secondRecord = (2004, 1, 375.2)
    val list1 = List(firstRecord, secondRecord)

    // our output of our method "parseRawData"
    val co2RecordWithType = CO2Record(firstRecord._1, firstRecord._2, firstRecord._3)
    val co2RecordWithType2 = CO2Record(secondRecord._1, secondRecord._2, secondRecord._3)
    val output = List(Some(co2RecordWithType), Some(co2RecordWithType2))

    // we call our function here to test our input and output
    assert(ClimateService.parseRawData(list1) == output)
  }

  //@TODO
  test("filterDecemberData") {
    // Input list with Some and None elements
    val listWithSomeNone = List(
      Some(CO2Record(2020, 12, 400.2)),
      None,
      Some(CO2Record(2021, 11, 410.5)),
      Some(CO2Record(2021, 12, 420.8)),
      None,
      Some(CO2Record(2022, 1, 430.3))
    )

    // Expected output list with CO2Records excluding December data
    val expectedOutput = List(
      CO2Record(2021, 11, 410.5),
      CO2Record(2022, 1, 430.3)
    )

    // Test the function with the input list and check if the output matches the expected output
    assert(ClimateService.filterDecemberData(listWithSomeNone) == expectedOutput)
  }

  test("getMinMax") {
    val list = List(
      CO2Record(2020, 1, 400.2),
      CO2Record(2020, 2, 410.5),
      CO2Record(2020, 3, 420.8),
      CO2Record(2020, 4, 430.3)
    )
    val expectedOutput = (400.2, 430.3)
    assert(ClimateService.getMinMax(list) == expectedOutput)

    val list2 = List(
      CO2Record(2021, 6, 410.5),
      CO2Record(2021, 7, 420.8),
      CO2Record(2021, 8, 430.3)
    )
    val expectedOutput2 = (410.5, 430.3)
    assert(ClimateService.getMinMax(list2) == expectedOutput2)
  }


  test("getMinMaxByYear") {
    val list = List(
      CO2Record(2019, 1, 411.23),
      CO2Record(2019, 2, 413.27),
      CO2Record(2019, 3, 414.83),
      CO2Record(2020, 1, 408.53),
      CO2Record(2020, 2, 410.24),
      CO2Record(2020, 3, 412.32),
      CO2Record(2021, 1, 409.56),
      CO2Record(2021, 2, 411.78),
      CO2Record(2021, 3, 414.21)
    )
    val expectedOutput = (409.56, 414.21)
    assert(ClimateService.getMinMaxByYear(list,2021) == expectedOutput)

    val list2 = List(
      CO2Record(2020, 6, 410.5),
      CO2Record(2021, 7, 420.8),
      CO2Record(2021, 8, 430.3),
      CO2Record(2020, 5, 520.4)
    )
    val expectedOutput2 = (410.5, 520.4)
    assert(ClimateService.getMinMaxByYear(list2,2020) == expectedOutput2)
  }
}