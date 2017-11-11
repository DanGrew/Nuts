# Nuts
Nutrient Usage Tracking System

I've been spending a lot of time working hard to lose weight and through lots of research and video watching, listening to the experts, I've moved on to macro counting. Part of this has involved calculations and planning and I felt there should be an easy (and free!) way of doing all this day to day. So here's Nuts!

# Goals

I've put in the calculation for your base maintenance calories and a few options surrounding that. This is to avoid the need to go online, find the calculation, grab a pen and paper and crunch some numbers. I've tried to leave it open so you can adjust any part of it as you go along. Calculators do not work the same for everyone! You have to tweak it based on how your body responds.

Here's a screen shot for what the goals tab looks like.

![Goal](/nuts/documentation/images/goal-screen.png)

# Database

So, the overall aim of the tool is to plan your food to fit your macros - how much of this, that and the other can I fit in. To do this, you fill out a database of foods (which can be a little tedious! but once it's done, you're set!). With the database you can build meals and plans - the highlight of the entire tool here... the system calculates the total macros and calories in the meal.

Here's a screen shot for the database, meals and meal creation.

![Database](/nuts/documentation/images/database-screen.png)

Hopefully the controls are simple enough, on the right there is a '+' and '-'. '+' adds a row which you then edit by double clicking on a cell and either selecting from a list or typing straight in (make sure you hit enter to accept the value). '-' removes the row. Columns are as they suggest, 'portion' refers to the percentage of that food/meal, so 2 portions = 200%.

# Plans

Building on the database, the plans tab allows you to create bigger meals. I use these for whole day plans, but you can use them however you like. Plans at the top, portions within the plan in the middle, and individual meal contents at the bottom. You can edit the meals directly from here - but be careful updating meals shared across plans as other plan macros will change. Again, the highlight of the the tool here... the system calculates the total macros for the plan, so if you use it like I do, you can plan your macros for the day.

![Plans](/nuts/documentation/images/plan-screen.png)

# Shopping List

While moving around and staying over at different places I was trying to plan what food I needed when, so I added a little tool that just lets you collect groups of plans, set the 'sold in' size of foods, and the shopping list will tell you how many you need to buy. Simple, but useful.

![Shopping List](/nuts/documentation/images/shopping-screen.png)

# Weigh-Ins

I use FitBit for all my exercise and weight tracking (I use the FitBit Surge and FitBit Aria Scales) and I'm a fan of statistics and tracking numbers. I've taken the information provided by my scales and put this into the system to create some pretty graphs. All of that you can find on FitBit anyway, but the interesting part I wanted to see was the running average (weekly average of the last 7 days, for every day) and plotting that against my day to day, as well as doing the same for my body fat records.

(Please be kind with my stats, I feel embarrassed to share them :)).

![Weigh Ins](/nuts/documentation/images/weigh-ins-screen.png)

Controls, again, hopefully simple. Double click on cells and type straight in to the table. Few controls on the right of the graph to configure how the graph looks.

# Tools

A very big frustration of mine... rice! Why do they not jsut tell you the damn calories in an understandable form, why do I need a calculator!?! Here's a simple dry weight tool to do the calculations for you.

![Tools](/nuts/documentation/images/dry-weight-calculator.png)

# Saving and Loading

I have only implemented what I've needed and I've kept it simple. The system loads from predefined files (located with the jar) on start up, no real need to load after that. Save is NOT automatic and saves EVERYTHING. Every time you make a change and want to keep it, just hit save at the top - easy. All data is stored in json files so you can reuse the data for anything you like. There are tons of random strings in the data, these are just unique ids to separate objects in the system - no name clashes! All data is your own and nothing to do with me :).
