/*******************************************************************************
 * Copyright 2009 Omnidroid - http://code.google.com/p/omnidroid 
 *  
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *  
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *     
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 *******************************************************************************/
package edu.nyu.cs.omnidroid.app.view.simple.model;

import java.util.ArrayList;

/**
 * A rule is a collection of <code>RuleNode</code> nodes. Each RuleNode instance has a single
 * <code>ModelItem</code> and a list of zero or more child <code>RuleNode</code> items. This
 * structure allows us to nest filters within other filters.
 * 
 * The root node <code>mNode</code> is the root event. Action nodes are always appended to the end
 * of the root node's child array. TODO: it may be convenient to move actions to their own separate
 * array.
 */
public class Rule {

  /** Database ID for this rule. */
  private final long databaseId;

  /** Name given to rule by user. */
  private String name;
  
  /** Description given to rule by user. */
  private String description;
  
  /** Is the rule enabled or disabled? */
  private boolean isEnabled;
  
  /**
   * The rule tree can look like: 
   * RootEvent 
   *   |- Filter1 
   *   |- Filter2 
   *   |   |- Filter3 
   *   |   |- Filter4 
   *   |- Action1 
   *   |- Action2
   */
  private RuleNode node;

  public Rule(long databaseId) {
    this.databaseId = databaseId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public long getDatabaseId() {
    return databaseId;
  }
  
  public boolean getIsEnabled() {
    return isEnabled;
  }
  
  public void setIsEnabled(boolean isEnabled) {
    this.isEnabled = isEnabled;
  }

  /**
   * Set the root event for the rule. This will clear all previous rule data since filters do not
   * always make sense for all the rule types.
   * 
   * @param event
   *          The root event to use.
   */
  public void setRootEvent(ModelEvent event) {
    if (node == null) {
      node = new RuleNode(null, event);
    } else {
      node.removeAllChildren();
      node.setItem(event);
    }
  }

  public RuleNode getRootNode() {
    return node;
  }

  /**
   * Check if any filters have been added to the tree.
   * 
   * @return True if one or more filters exist.
   */
  public boolean getHasAnyFilters() {
    int firstActionPos = getFirstActionPosition();
    return node.getChildren().size() > 0 && (firstActionPos < 0 || firstActionPos != 0);
  }

  /**
   * Builds a list of nodes which are top-level filter branches.
   * 
   * @return An ArrayList of node branches containing filters.
   */
  public ArrayList<RuleNode> getFilterBranches() {
    ArrayList<RuleNode> filters = new ArrayList<RuleNode>();
    int stopIndex = node.getChildren().size();
    int firstActionIndex = getFirstActionPosition();
    if (firstActionIndex > -1) {
      stopIndex = firstActionIndex;
    }
    for (int i = 0; i < stopIndex; i++) {
      filters.add(node.getChildren().get(i));
    }

    return filters;
  }

  /**
   * Extract all {@link ModelRuleAction} instances out of the tree and add them to the a list.
   * 
   * @return All actions that are part of the rule.
   */
  public ArrayList<ModelRuleAction> getActions() {
    ArrayList<ModelRuleAction> actions = new ArrayList<ModelRuleAction>();
    int firstActionIndex = getFirstActionPosition();
    if (firstActionIndex > -1) {
      int numTopLevelChildren = node.getChildren().size();
      for (int i = firstActionIndex; i < numTopLevelChildren; i++) {
        actions.add((ModelRuleAction) node.getChildren().get(i).getItem());
      }
    }
    
    return actions;
  }

  /**
   * Find the first index of the root node that is an action.
   * 
   * @return The first index of an action, or -1 if no actions present.
   */
  public int getFirstActionPosition() {
    // Actions are stored as siblings directly under the root event.
    // This is enforced by the user interface.
    int numTopLevelChildren = node.getChildren().size();
    for (int i = 0; i < numTopLevelChildren; i++) {
      if (node.getChildren().get(i).getItem() instanceof ModelRuleAction) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Will build a natural language string of the entire rule.
   * 
   * @return A natural language string of the entire rule.
   */
  public String getNaturalLanguageString() {
    // TODO: Consider using visitor pattern here instead.
    StringBuilder sb = new StringBuilder(500);
    buildNaturalLanguageString(node, sb);
    return sb.toString();
  }

  /**
   * Recursively works on each node to get their natural language representation.
   * 
   * @param node
   *          The current node to work on.
   * @param sb
   *          The string builder to append new text to.
   */
  private void buildNaturalLanguageString(RuleNode node, StringBuilder sb) {
    sb.append(node.getItem().getDescriptionShort());
    sb.append(", ");
    int numChildren = node.getChildren().size();
    for (int i = 0; i < numChildren; i++) {
      buildNaturalLanguageString(node.getChildren().get(i), sb);
    }
  }
}