/*
 * Copyright 2010 Henry Coles
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package org.pitest.mutationtest.engine.gregor.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.pitest.functional.F;
import org.pitest.functional.FCollection;
import org.pitest.functional.prelude.Prelude;
import org.pitest.help.Help;
import org.pitest.help.PitHelpError;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.mutators.ArgumentPropagationMutator;
import org.pitest.mutationtest.engine.gregor.mutators.ConditionalsBoundaryMutator;
import org.pitest.mutationtest.engine.gregor.mutators.ConstructorCallMutator;
import org.pitest.mutationtest.engine.gregor.mutators.IncrementsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.InlineConstantMutator;
import org.pitest.mutationtest.engine.gregor.mutators.InvertNegsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.MathMutator;
import org.pitest.mutationtest.engine.gregor.mutators.NegateConditionalsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.NonVoidMethodCallMutator;
import org.pitest.mutationtest.engine.gregor.mutators.RemoveConditionalMutator;
import org.pitest.mutationtest.engine.gregor.mutators.RemoveConditionalMutator.Choice;
import org.pitest.mutationtest.engine.gregor.mutators.ReturnValsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.VoidMethodCallMutator;
import org.pitest.mutationtest.engine.gregor.mutators.experimental.NakedReceiverMutator;
import org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveIncrementsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator;
import org.pitest.mutationtest.engine.gregor.mutators.experimental.SwitchMutator;
import org.pitest.mutationtest.engine.gregor.mutators.Joke.ABSMutator;
import org.pitest.mutationtest.engine.gregor.mutators.Joke.AODMutator;
import org.pitest.mutationtest.engine.gregor.mutators.Joke.AORMutatorAdd;
import org.pitest.mutationtest.engine.gregor.mutators.Joke.AORMutatorDivide;
import org.pitest.mutationtest.engine.gregor.mutators.Joke.AORMutatorMinus;
import org.pitest.mutationtest.engine.gregor.mutators.Joke.AORMutatorMultiply;
import org.pitest.mutationtest.engine.gregor.mutators.Joke.CRCRMutatorDecrement;
import org.pitest.mutationtest.engine.gregor.mutators.Joke.CRCRMutatorIncrement;
import org.pitest.mutationtest.engine.gregor.mutators.Joke.CRCRMutatorNegation;
import org.pitest.mutationtest.engine.gregor.mutators.Joke.CRCRMutatorOne;
import org.pitest.mutationtest.engine.gregor.mutators.Joke.CRCRMutatorZero;
import org.pitest.mutationtest.engine.gregor.mutators.Joke.OBBNMutator;
import org.pitest.mutationtest.engine.gregor.mutators.Joke.RORMutatorEqual;
import org.pitest.mutationtest.engine.gregor.mutators.Joke.RORMutatorGreater;
import org.pitest.mutationtest.engine.gregor.mutators.Joke.RORMutatorGreaterEqual;
import org.pitest.mutationtest.engine.gregor.mutators.Joke.RORMutatorNotEqual;
import org.pitest.mutationtest.engine.gregor.mutators.Joke.RORMutatorSmaller;
import org.pitest.mutationtest.engine.gregor.mutators.Joke.RORMutatorSmallerEqual;
import org.pitest.mutationtest.engine.gregor.mutators.Joke.UOIMutatorDecrement;
import org.pitest.mutationtest.engine.gregor.mutators.Joke.UOIMutatorIncrement;
import org.pitest.mutationtest.engine.gregor.mutators.Joke.UOIMutatorInvert;
import org.pitest.mutationtest.engine.gregor.mutators.Joke.UOIMutatorMinus;
public final class Mutator {

  private static final Map<String, Iterable<MethodMutatorFactory>> MUTATORS = new LinkedHashMap<String, Iterable<MethodMutatorFactory>>();

  static {
    // 6367
    add("ABS_MUTATOR", ABSMutator.ABS_MUTATOR);
    add("AOD_MUTATOR", AODMutator.AOD_MUTATOR);
    add("AOR_MUTATOR_ADD", AORMutatorAdd.AOR_MUTATOR_ADD);
    add("AOR_MUTATOR_DIVIDE", AORMutatorDivide.AOR_MUTATOR_DIVIDE);
    add("AOR_MUTATOR_MINUS", AORMutatorMinus.AOR_MUTATOR_MINUS);
    add("AOR_MUTATOR_MULTIPLY", AORMutatorMultiply.AOR_MUTATOR_MULTIPLY);
    add("CRCR_MUTATOR_DECREMENT", CRCRMutatorDecrement.CRCR_MUTATOR_DECREMENT);
    add("CRCR_MUTATOR_INCREMENT", CRCRMutatorIncrement.CRCR_MUTATOR_INCREMENT);
    add("CRCR_MUTATOR_NEGATION", CRCRMutatorNegation.CRCR_MUTATOR_NEGATION);
    add("CRCR_MUTATOR_ONE", CRCRMutatorOne.CRCR_MUTATOR_ONE);
    add("CRCR_MUTATOR_ZERO", CRCRMutatorZero.CRCR_MUTATOR_ZERO);
    add("OBBN_MUTATOR", OBBNMutator.OBBN_MUTATOR);
    add("ROR_MUTATOR_TOEQUAL", RORMutatorEqual.ROR_MUTATOR_TOEQUAL);
    add("ROR_MUTATOR_TOGREATER", RORMutatorGreater.ROR_MUTATOR_TOGREATER);
    add("ROR_MUTATOR_TOGTREATEREQUAL", RORMutatorGreaterEqual.ROR_MUTATOR_TOGTREATEREQUAL);
    add("ROR_MUTATOR_TONOTEQUAL", RORMutatorNotEqual.ROR_MUTATOR_TONOTEQUAL);
    add("ROR_MUTATOR_TOSMALLER", RORMutatorSmaller.ROR_MUTATOR_TOSMALLER);
    add("ROR_MUTATOR_TOSMALLEREQUAL", RORMutatorSmallerEqual.ROR_MUTATOR_TOSMALLEREQUAL);
    add("UOI_MUTATOR_DECREMENT", UOIMutatorDecrement.DECREMENT);
    add("UOI_MUTATOR_INCREMENT", UOIMutatorIncrement.INCREMENT);
    add("UOI_MUTATOR_INVERT", UOIMutatorInvert.INVERT);
    add("UOI_MUTATOR_MINUS", UOIMutatorMinus.MINUS);
    
    /**
     * Default mutator that inverts the negation of integer and floating point
     * numbers.
     */
    add("INVERT_NEGS", InvertNegsMutator.INVERT_NEGS_MUTATOR);

    /**
     * Default mutator that mutates the return values of methods.
     */
    add("RETURN_VALS", ReturnValsMutator.RETURN_VALS_MUTATOR);

    /**
     * Optional mutator that mutates integer and floating point inline
     * constants.
     */
    add("INLINE_CONSTS", new InlineConstantMutator());

    /**
     * Default mutator that mutates binary arithmetic operations.
     */
    add("MATH", MathMutator.MATH_MUTATOR);

    /**
     * Default mutator that removes method calls to void methods.
     *
     */
    add("VOID_METHOD_CALLS", VoidMethodCallMutator.VOID_METHOD_CALL_MUTATOR);

    /**
     * Default mutator that negates conditionals.
     */
    add("NEGATE_CONDITIONALS",
        NegateConditionalsMutator.NEGATE_CONDITIONALS_MUTATOR);

    /**
     * Default mutator that replaces the relational operators with their
     * boundary counterpart.
     */
    add("CONDITIONALS_BOUNDARY",
        ConditionalsBoundaryMutator.CONDITIONALS_BOUNDARY_MUTATOR);

    /**
     * Default mutator that mutates increments, decrements and assignment
     * increments and decrements of local variables.
     */
    add("INCREMENTS", IncrementsMutator.INCREMENTS_MUTATOR);

    /**
     * Optional mutator that removes local variable increments.
     */

    add("REMOVE_INCREMENTS", RemoveIncrementsMutator.REMOVE_INCREMENTS_MUTATOR);

    /**
     * Optional mutator that removes method calls to non void methods.
     */
    add("NON_VOID_METHOD_CALLS",
        NonVoidMethodCallMutator.NON_VOID_METHOD_CALL_MUTATOR);

    /**
     * Optional mutator that replaces constructor calls with null values.
     */
    add("CONSTRUCTOR_CALLS", ConstructorCallMutator.CONSTRUCTOR_CALL_MUTATOR);

    /**
     * Removes conditional statements so that guarded statements always execute
     * The EQUAL version ignores LT,LE,GT,GE, which is the default behavior,
     * ORDER version mutates only those.
     */

    add("REMOVE_CONDITIONALS_EQ_IF", new RemoveConditionalMutator(Choice.EQUAL,
        true));
    add("REMOVE_CONDITIONALS_EQ_ELSE", new RemoveConditionalMutator(
        Choice.EQUAL, false));
    add("REMOVE_CONDITIONALS_ORD_IF", new RemoveConditionalMutator(
        Choice.ORDER, true));
    add("REMOVE_CONDITIONALS_ORD_ELSE", new RemoveConditionalMutator(
        Choice.ORDER, false));
    addGroup("REMOVE_CONDITIONALS", RemoveConditionalMutator.makeMutators());

    /**
     * Experimental mutator that removed assignments to member variables.
     */
    add("EXPERIMENTAL_MEMBER_VARIABLE",
        new org.pitest.mutationtest.engine.gregor.mutators.experimental.MemberVariableMutator());

    /**
     * Experimental mutator that swaps labels in switch statements
     */
    add("EXPERIMENTAL_SWITCH",
        new org.pitest.mutationtest.engine.gregor.mutators.experimental.SwitchMutator());

    /**
     * Experimental mutator that replaces method call with one of its parameters
     * of matching type
     */
    add("EXPERIMENTAL_ARGUMENT_PROPAGATION",
        ArgumentPropagationMutator.ARGUMENT_PROPAGATION_MUTATOR);

    /**
     * Experimental mutator that replaces method call with this
     */
    add("EXPERIMENTAL_NAKED_RECEIVER", NakedReceiverMutator.NAKED_RECEIVER);

    addGroup("REMOVE_SWITCH", RemoveSwitchMutator.makeMutators());
    addGroup("DEFAULTS", defaults());
    addGroup("STRONGER", stronger());
    addGroup("ALL", all());
  }

  public static Collection<MethodMutatorFactory> all() {
    return fromStrings(MUTATORS.keySet());
  }

  private static Collection<MethodMutatorFactory> stronger() {
    return combine(
        defaults(),
        group(new RemoveConditionalMutator(Choice.EQUAL, false),
            new SwitchMutator()));
  }

  private static Collection<MethodMutatorFactory> combine(
      Collection<MethodMutatorFactory> a, Collection<MethodMutatorFactory> b) {
    List<MethodMutatorFactory> l = new ArrayList<MethodMutatorFactory>(a);
    l.addAll(b);
    return l;
  }

  /**
   * Default set of mutators - designed to provide balance between strength and
   * performance
   */
  public static Collection<MethodMutatorFactory> defaults() {
    return group(InvertNegsMutator.INVERT_NEGS_MUTATOR,
        ReturnValsMutator.RETURN_VALS_MUTATOR, MathMutator.MATH_MUTATOR,
        VoidMethodCallMutator.VOID_METHOD_CALL_MUTATOR,
        NegateConditionalsMutator.NEGATE_CONDITIONALS_MUTATOR,
        ConditionalsBoundaryMutator.CONDITIONALS_BOUNDARY_MUTATOR,
        IncrementsMutator.INCREMENTS_MUTATOR);
  }

  private static Collection<MethodMutatorFactory> group(
      final MethodMutatorFactory... ms) {
    return Arrays.asList(ms);
  }

  public static Collection<MethodMutatorFactory> byName(final String name) {
    return FCollection.map(MUTATORS.get(name),
        Prelude.id(MethodMutatorFactory.class));
  }

  private static void add(final String key, final MethodMutatorFactory value) {
    MUTATORS.put(key, Collections.singleton(value));
  }

  private static void addGroup(final String key,
      final Iterable<MethodMutatorFactory> value) {
    MUTATORS.put(key, value);
  }

  public static Collection<MethodMutatorFactory> fromStrings(
      final Collection<String> names) {
    final Set<MethodMutatorFactory> unique = new TreeSet<MethodMutatorFactory>(
        compareId());

    FCollection.flatMapTo(names, fromString(), unique);
    return unique;
  }

  private static Comparator<? super MethodMutatorFactory> compareId() {
    return new Comparator<MethodMutatorFactory>() {
      @Override
      public int compare(final MethodMutatorFactory o1,
          final MethodMutatorFactory o2) {
        return o1.getGloballyUniqueId().compareTo(o2.getGloballyUniqueId());
      }
    };
  }

  private static F<String, Iterable<MethodMutatorFactory>> fromString() {
    return new F<String, Iterable<MethodMutatorFactory>>() {
      @Override
      public Iterable<MethodMutatorFactory> apply(final String a) {
        Iterable<MethodMutatorFactory> i = MUTATORS.get(a);
        if (i == null) {
          throw new PitHelpError(Help.UNKNOWN_MUTATOR, a);
        }
        return i;
      }
    };
  }

}
