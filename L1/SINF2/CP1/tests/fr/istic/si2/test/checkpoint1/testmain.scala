package fr.istic.si2.test.checkpoint1

import org.junit.Test
import org.junit.Assert._
import util.Random
import fr.istic.si2.testerApp._
import fr.istic.si2.moreAssertions._
import fr.istic.si2.checkpoint1.ExercicesCP1._
import fr.istic.si2.checkpoint1.ExercicesCP1

class ExosBasiquesDefinitionTest {

  val _ = new AppInit(ExercicesCP1)
  val rand = new Random()

  /**
   * La fonction valeurAbsolue est bien définie
   */
  @Test(timeout = 3000)
  def valeurAbsolueDefined {
    isDefined(valeurAbsolue(rand.nextInt()))
  }

  /**
   * La fonction abc est bien définie
   */
  @Test(timeout = 3000)
  def abcDefined {
    isDefined(abc(rand.nextString(5)))
  }

  /**
   * La fonction estMin est bien définie
   */
  @Test(timeout = 3000)
  def estMinDefined {
    isDefined(estMin(rand.nextInt(), rand.nextInt(), rand.nextInt()))
  }

}

class ExercicesCP1CorrectionTest {

  val rand = new Random()
  val _ = new AppInit(ExercicesCP1)

  val exos = Consignes.parseFile("main.scala", "fr.istic.si2.checkpoint1")

  /**
   * La fonction valeurAbsolue ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 3000)
  def valeurAbsolueNoRTE {
    for (_ <- 1 to 10000) noRTE(valeurAbsolue(rand.nextInt()))
  }

  /**
   * La fonction abc ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 3000)
  def abcNoRTE {
    for (_ <- 1 to 10000) noRTE(abc(rand.nextString(rand.nextInt(7))))
  }

  /**
   * La fonction estMin ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 3000)
  def estMinNoRTE {
    for (_ <- 1 to 10000) noRTE(estMin(rand.nextInt(), rand.nextInt(), rand.nextInt()))
  }

  /**
   * La fonction valeurAbsolue est correcte pour les positifs
   */
  @Test(timeout = 3000)
  def valeurAbsolueOKpos {
    for (_ <- 1 to 1000) {
      val x = rand.nextInt(1000)
      assertEquals(x, valeurAbsolue(x))
    }
  }
  /**
   * La fonction valeurAbsolue est correcte pour les négatifs
   */
  @Test(timeout = 3000)
  def valeurAbsolueOKneg {
    for (_ <- 1 to 1000) {
      val x = rand.nextInt(1000)
      assertEquals(x, valeurAbsolue(-x))
    }
  }
  /**
   * La fonction valeurAbsolue est correcte pour 0
   */
  @Test(timeout = 3000)
  def valeurAbsolueOKzero {
    assertEquals(0, valeurAbsolue(0))
  }

  /**
   * La fonction abc est correcte pour les cas vrais
   */
  @Test(timeout = 3000)
  def abcOKtrue {
    for (s <- "a" :: "b" :: "c" :: Nil) {
      assertTrue(abc(s))
    }
  }
  /**
   * La fonction abc est correcte pour les cas faux
   */
  @Test(timeout = 10000)
  def abcOKfalse {
    val set = "a" :: "b" :: "c" :: Nil
    var nbtest = 1000
    while (nbtest > 0) {
      val s1 = rand.nextString(1)
      if (!set.contains(s1)) {
        assertFalse(abc(s1))
        nbtest -= 1
      }
    }

    for (_ <- 1 to 1000) {
      val s2 = rand.nextString(4)
      assertFalse(abc(s2))
    }

  }
  
  /**
   * La fonction abc n'utilise pas de conditionnelle.
   */
  @Test
  def abcNoConditional {
    val noCond = Consignes.checkFunctionP("abc", exos, Consignes.hasNoConditional(_))
    if (!noCond) {
      fail("La fonction abc ne doit pas utiliser de conditionnelle. Utilisez un pattern matching.")
    }
  }
  
  /**
   * La fonction abc utilise un pattern-matching
   */
  @Test(timeout = 30000)
  def abcPatternMatching {
    val pm = Consignes.checkFunctionP("abc", exos, Consignes.hasPatternMatch(_))
    if (!pm) {
      fail("La fonction abc doit utiliser un pattern matching.")
    }
  }

  
  /**
   * La fonction estMin est correcte pour les cas vrais
   */
  @Test(timeout = 3000)
  def estMinOKtrue {
    for (_ <- 1 to 1000) {
      val x = rand.nextInt(100)
      val y = rand.nextInt(100)
      assertTrue(estMin(x, x, x + y))
      assertTrue(estMin(x, x + y, x))
      assertTrue(estMin(-x, -x, x + y))
      assertTrue(estMin(-x, x + y, -x))
    }
  }
  /**
   * La fonction estMin est correcte pour les cas faux
   */
  @Test(timeout = 3000)
  def estMinOKfalse {
    for (_ <- 1 to 1000) {
      val x = rand.nextInt(100)
      val y = rand.nextInt(100) + 2
      assertFalse(estMin(x + y, x, x + y))
      assertFalse(estMin(x + y, x + y, x))
      assertFalse(estMin(x + y, -x, x + y))
      assertFalse(estMin(x + y, x + y, -x))
    }
    
    var nbtest = 1000
    while (nbtest > 0) {
      val x = rand.nextInt()
      val y = rand.nextInt()
      val z = rand.nextInt()
      if (x != y && x != z) {
        assertFalse(estMin(x,y,z))
        nbtest -= 1
      }
    }

  }
  /**
   * La fonction estMin est correcte pour le cas où x = y
   */
  @Test(timeout = 3000)
  def estMinOKeq {
    for (_ <- 1 to 1000) {
      val x = (if (rand.nextBoolean()) 1 else -1) * rand.nextInt(100)
      assertTrue(estMin(x,x,x))
    }
  }
  
  /**
   * La fonction estMin n'utilise pas de conditionnelle.
   */
  @Test
  def estMinNoConditional {
    val noCond = Consignes.checkFunctionP("estMin", exos, Consignes.hasNoConditional(_))
    if (!noCond) {
      fail("La fonction estMin ne doit pas utiliser de conditionnelle. Utilisez des opérateurs booléens.")
    }
  }
}
