####
#
# github comment settings
#
####
github.dismiss_out_of_range_messages

####
#
# for PR
#
####
# Make it more obvious that a PR is a work in progress and shouldn't be merged yet
warn("PR is classed as Work in Progress") if github.pr_title.include? "[WIP]"

# Warn when there is a big PR
warn("Big PR") if git.lines_of_code > 500

# Don't let testing shortcuts get into master by accident
fail("fdescribe left in tests") if `grep -r fdescribe specs/ `.length > 1
fail("fit left in tests") if `grep -r fit specs/ `.length > 1

# Warn when PR has no assignees
warn("A pull request must have some assignees") if github.pr_json["assignee"].nil?

####
#
# Android Lint
#
####
android_lint.gradle_task = "studyplus-android-sdk:lintDebug"
android_lint.report_file = "studyplus-android-sdk/build/reports/lint-results-debug.xml"
android_lint.filtering = true
android_lint.lint(inline_mode: true)