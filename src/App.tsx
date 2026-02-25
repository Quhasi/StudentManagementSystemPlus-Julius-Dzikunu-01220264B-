/**
 * @license
 * SPDX-License-Identifier: Apache-2.0
 */

import React from 'react';
import { 
  Layout, 
  Database, 
  FileText, 
  ShieldCheck, 
  Terminal, 
  Github, 
  Download,
  CheckCircle2,
  Info
} from 'lucide-react';

export default function App() {
  return (
    <div className="min-h-screen bg-[#f5f5f0] text-[#1a1a1a] font-serif p-8">
      <div className="max-w-5xl mx-auto">
        {/* Header */}
        <header className="border-b border-black/10 pb-8 mb-12">
          <div className="flex justify-between items-end">
            <div>
              <h1 className="text-6xl font-black tracking-tighter uppercase mb-2">SMS Plus</h1>
              <p className="text-xl italic text-black/60">Student Management System Professional Edition</p>
            </div>
            <div className="text-right">
              <div className="text-xs font-mono uppercase tracking-widest opacity-50 mb-1">Status</div>
              <div className="flex items-center gap-2 text-emerald-600 font-bold">
                <CheckCircle2 size={16} />
                CODE GENERATED
              </div>
            </div>
          </div>
        </header>

        {/* Main Content */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-12">
          {/* Left Column: Project Info */}
          <div className="md:col-span-2 space-y-12">
            <section>
              <h2 className="text-2xl font-bold mb-4 flex items-center gap-3">
                <Info size={24} />
                Project Overview
              </h2>
              <p className="text-lg leading-relaxed mb-6">
                This project is a complete JavaFX desktop application built with a layered architecture (Domain, Repository, Service, UI). 
                It uses SQLite for persistence and Maven for dependency management. All functional requirements from your assignment 
                have been implemented in the source code.
              </p>
              
              <div className="grid grid-cols-2 gap-4">
                <div className="p-6 bg-white rounded-3xl shadow-sm border border-black/5">
                  <Database className="mb-4 text-emerald-600" />
                  <h3 className="font-bold mb-1">SQLite Persistence</h3>
                  <p className="text-sm opacity-70">Safe JDBC implementation with prepared statements.</p>
                </div>
                <div className="p-6 bg-white rounded-3xl shadow-sm border border-black/5">
                  <ShieldCheck className="mb-4 text-emerald-600" />
                  <h3 className="font-bold mb-1">Data Validation</h3>
                  <p className="text-sm opacity-70">Strict rules enforced at both UI and Service layers.</p>
                </div>
                <div className="p-6 bg-white rounded-3xl shadow-sm border border-black/5">
                  <FileText className="mb-4 text-emerald-600" />
                  <h3 className="font-bold mb-1">Reporting Engine</h3>
                  <p className="text-sm opacity-70">Top performers, at-risk tracking, and GPA distribution.</p>
                </div>
                <div className="p-6 bg-white rounded-3xl shadow-sm border border-black/5">
                  <Terminal className="mb-4 text-emerald-600" />
                  <h3 className="font-bold mb-1">Unit Testing</h3>
                  <p className="text-sm opacity-70">JUnit 5 tests covering core validation and logic.</p>
                </div>
              </div>
            </section>

            <section>
              <h2 className="text-2xl font-bold mb-4">Implementation Details</h2>
              <ul className="space-y-3 font-mono text-sm">
                <li className="flex items-center gap-2">
                  <span className="w-2 h-2 bg-black rounded-full"></span>
                  Layered Architecture: UI, Domain, Service, Repository, Util
                </li>
                <li className="flex items-center gap-2">
                  <span className="w-2 h-2 bg-black rounded-full"></span>
                  JavaFX FXML for clean UI/Logic separation
                </li>
                <li className="flex items-center gap-2">
                  <span className="w-2 h-2 bg-black rounded-full"></span>
                  Custom Logging system writing to data/app.log
                </li>
                <li className="flex items-center gap-2">
                  <span className="w-2 h-2 bg-black rounded-full"></span>
                  CSV Import/Export with error reporting
                </li>
              </ul>
            </section>
          </div>

          {/* Right Column: Actions/Files */}
          <div className="space-y-8">
            <div className="bg-black text-white p-8 rounded-3xl">
              <h3 className="text-xl font-bold mb-4 flex items-center gap-2">
                <Download size={20} />
                Ready to Launch
              </h3>
              <p className="text-sm opacity-70 mb-6">
                The Java source code is ready. You can copy these files to your local VS Code environment.
              </p>
              <div className="space-y-4">
                <div className="p-3 bg-white/10 rounded-xl border border-white/10 text-xs font-mono">
                  pom.xml
                </div>
                <div className="p-3 bg-white/10 rounded-xl border border-white/10 text-xs font-mono">
                  src/main/java/com/sms/...
                </div>
                <div className="p-3 bg-white/10 rounded-xl border border-white/10 text-xs font-mono">
                  RUN_VM_OPTIONS.txt
                </div>
              </div>
            </div>

            <div className="p-8 bg-emerald-50 rounded-3xl border border-emerald-100">
              <h3 className="text-xl font-bold mb-2 flex items-center gap-2">
                <Github size={20} />
                Submission
              </h3>
              <p className="text-sm text-emerald-900/70">
                Don't forget to push this code to your GitHub repository as required by the assignment.
              </p>
            </div>
          </div>
        </div>

        {/* Footer */}
        <footer className="mt-24 pt-8 border-t border-black/10 text-center text-sm opacity-40">
          <p>© 2026 Student Management System Plus • Individual Assignment Submission</p>
        </footer>
      </div>
    </div>
  );
}

