# CoX Censor

Censors Chambers of Xeric unique names in chat until the reward chest is opened.
If the loot is a new unique, it shows a delayed collection-log popup and saves a screenshot.

## What it does

1. Censors CoX unique names in friends chat and clan broadcasts (and matching collection-log / valuable-drop lines) until you open the reward chest.
2. Hides the native collection-log popup for that unique so nobody sees the item name on-screen.
3. When the CoX chest is opened:
   - Restores the real chat messages
   - Opens chat on the Game tab (so the screenshot shows the collection-log game message)
   - Shows a custom collection-log popup
   - Saves a screenshot that includes the popup

Screenshots use RuneLite's normal location and naming:

```text
.runelite/screenshots/<character>/Collection Log/
Collection log (Twisted bow) 2026-09-14_10-34-00.png
```

If you want to see the loot early, turn the plugin off.

## Config options

- **Censor raid loot in chat** — hide unique names until the chest opens
- **Only censor your purples** — ignore other players' uniques
- **Delay collection log popup until chest opens** — hide the native popup and replay it on chest open
- **Take collection log screenshot** — capture the delayed popup
- **Open Game chat on reveal** — switch chat to Game before the popup screenshot
- **Dark collection log popup** — alternate popup style

## Credits

Originally by Karambtwo. Maintained by Rikten X. Delayed collection-log popup and screenshot work by JJ.
